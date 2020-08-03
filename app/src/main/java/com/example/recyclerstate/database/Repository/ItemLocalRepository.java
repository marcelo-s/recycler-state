package com.example.recyclerstate.database.Repository;

import com.example.recyclerstate.database.Dao.IItemDAO;
import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.IItemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ItemLocalRepository implements IItemRepository {

    private final IItemDAO itemDAO;

    @Inject
    public ItemLocalRepository(IItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public Single<List<Item>> getAll() {
        return itemDAO.getAll();
    }

    public Single<Item> getItem(long id) {
        return itemDAO.getItem(id);
    }

    public Single<List<Long>> insertAll(List<Item> items) {
        return itemDAO.insertAll(items);
    }
}
