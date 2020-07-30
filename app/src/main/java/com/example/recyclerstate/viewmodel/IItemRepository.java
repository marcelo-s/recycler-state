package com.example.recyclerstate.viewmodel;

import com.example.recyclerstate.viewmodel.Entity.Item;

import java.util.List;

import io.reactivex.Single;

public interface IItemRepository {

    Single<List<Item>> getAll();

    Single<Item> getItem(long id);

    Single<List<Long>> insertAll(List<Item> items);
}
