package com.example.recyclerstate.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recyclerstate.database.AppDatabase;
import com.example.recyclerstate.database.Repository.ItemLocalRepository;
import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.state.ItemDetailErrorState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadedState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadingState;
import com.example.recyclerstate.viewmodel.state.ItemDetailState;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemsViewModelImpl extends AndroidViewModel implements IItemsViewModel {

    public static final String TAG = "ItemViewModel";
    private IItemRepository IItemRepository;
    private AppDatabase appDatabase;
    private MutableLiveData<List<Item>> itemsLiveData;
    private MutableLiveData<ItemDetailState> itemDetailStateLiveData;
    private Disposable getAllItemsSubscription;
    private Disposable getItemSubscription;
    private Disposable insertAllItemsSubscription;
    private CompositeDisposable compositeDisposable;

    enum DBOperation {
        INSERT, GET
    }

    public ItemsViewModelImpl(@NonNull Application application) {
        super(application);
        setDatabase();
        setItemRepository();
        init();
    }

    private void setItemRepository() {
        this.IItemRepository = new ItemLocalRepository(appDatabase.itemDAO());
    }

    private void setDatabase() {
        this.appDatabase = AppDatabase.getInstance(getApplication(), this::getOnCreateDBCallback);
    }

    private void init() {
        initializeCompositeDisposables();
        initializeAllLiveDataFields();
        // Initial call to DB in order to initialize DB
        subscribeToItemsFromDB();
    }

    private void getOnCreateDBCallback() {
        List<Item> items = getPrepopulateItems();
        insertAllItemsSubscription = IItemRepository.insertAll(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onInitialDataInserted,
                        onItemDBError(DBOperation.INSERT)
                );
    }

    private void onInitialDataInserted(List<Long> itemsIds) {
        String message = String.format("Initial Items inserted into DB: %s",
                itemsIds.size());
        Log.e("DB", message);
        insertAllItemsSubscription.dispose();
        subscribeToItemsFromDB();
    }

    private void subscribeToItemsFromDB() {
        removeSubscriptionFromCompositeDisposable(getAllItemsSubscription);
        getAllItemsSubscription = IItemRepository.getAll()
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
        return Item.builder()
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

    @Override
    public LiveData<List<Item>> getItems() {
        return this.itemsLiveData;
    }

    @Override
    public LiveData<ItemDetailState> getItemDetailState() {
        return itemDetailStateLiveData;
    }

    @Override
    public void loadItem(long itemId) {
        setItemDetailState(ItemDetailLoadingState.getInstance());
        removeSubscriptionFromCompositeDisposable(getItemSubscription);
        getItemSubscription = IItemRepository.getItem(itemId)
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

    public void setItemDetailState(ItemDetailState itemDetailState) {
        this.itemDetailStateLiveData.setValue(itemDetailState);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
