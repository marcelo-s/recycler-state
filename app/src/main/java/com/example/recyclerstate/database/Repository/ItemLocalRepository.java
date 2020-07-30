package com.example.recyclerstate.database.Repository;

import com.example.recyclerstate.database.Dao.IItemDAO;
import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.IItemRepository;

import java.util.List;

import io.reactivex.Single;

public class ItemLocalRepository implements IItemRepository {

    private final IItemDAO IItemDAO;

    public ItemLocalRepository(IItemDAO IItemDAO) {
        this.IItemDAO = IItemDAO;
    }

    public Single<List<Item>> getAll() {
        return IItemDAO.getAll();
    }

    public Single<Item> getItem(long id) {
        return IItemDAO.getItem(id);
    }

    public Single<List<Long>> insertAll(List<Item> items) {
        return IItemDAO.insertAll(items);
    }
}
