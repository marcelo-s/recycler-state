package com.example.recyclerstate.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.state.IItemDetailState;
import com.example.recyclerstate.viewmodel.state.ItemDetailErrorState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadedState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadingState;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemsViewModelImpl extends ViewModel {

    public static final String TAG = "ItemViewModel";
    private IItemRepository itemRepository;
    private MutableLiveData<List<Item>> itemsLiveData;
    private MutableLiveData<IItemDetailState> itemDetailStateLiveData;
    private Disposable getAllItemsSubscription;
    private Disposable getItemSubscription;
    private CompositeDisposable compositeDisposable;

    enum DBOperation {
        GET
    }

    @ViewModelInject
    public ItemsViewModelImpl(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        init();
    }

    private void init() {
        initializeCompositeDisposables();
        initializeAllLiveDataFields();
        subscribeToItemsFromDB();
    }

    private void subscribeToItemsFromDB() {
        removeSubscriptionFromCompositeDisposable(getAllItemsSubscription);
        getAllItemsSubscription = itemRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onItemsLoaded,
                        onItemDBError(DBOperation.GET)
                );
    }

    private List<Item> getPrepopulateItems() {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(this::mapIntToItem)
                .collect(Collectors.toList());
    }

    private Item mapIntToItem(int i) {
        return new Item.Builder()
                .name("Item " + i)
                .description("Description for item " + i)
                .build();
    }

    private void initializeCompositeDisposables() {
        this.compositeDisposable = new CompositeDisposable();
    }

    private void onItemsLoaded(List<Item> items) {
        Log.e(TAG, "ON ITEMS LOADED");
        itemsLiveData.setValue(items);
    }

    private void initializeAllLiveDataFields() {
        this.itemsLiveData = new MutableLiveData<>();
        this.itemDetailStateLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Item>> getItems() {
        return this.itemsLiveData;
    }

    public LiveData<IItemDetailState> getItemDetailState() {
        return itemDetailStateLiveData;
    }

    public void loadItem(long itemId) {
        setItemDetailState(ItemDetailLoadingState.getInstance());
        removeSubscriptionFromCompositeDisposable(getItemSubscription);
        getItemSubscription = itemRepository.getItem(itemId)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onItemLoaded,
                        onItemDBError(DBOperation.GET)
                );
    }

    private void removeSubscriptionFromCompositeDisposable(Disposable subscription) {
        if (subscription != null) {
            compositeDisposable.remove(subscription);
        }
    }

    private Consumer<? super Throwable> onItemDBError(DBOperation dbOperation) {
        return throwable -> {
            Log.e(TAG, "Error with dp operation : " + dbOperation.name());
            throwable.printStackTrace();
            setItemDetailState(ItemDetailErrorState.getInstance());
        };
    }

    private void onItemLoaded(Item item) {
        setItemDetailState(ItemDetailLoadedState.of(item));
    }

    private void setItemDetailState(IItemDetailState itemDetailState) {
        this.itemDetailStateLiveData.setValue(itemDetailState);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
