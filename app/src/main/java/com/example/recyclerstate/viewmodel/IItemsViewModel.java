package com.example.recyclerstate.viewmodel;

import androidx.lifecycle.LiveData;

import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.state.ItemDetailState;

import java.util.List;

public interface IItemsViewModel {
    void loadItem(long itemId);

    LiveData<List<Item>> getItems();

    LiveData<ItemDetailState> getItemDetailState();
}
