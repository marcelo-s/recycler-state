package com.example.recyclerstate.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recyclerstate.viewmodel.Entity.Item;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface IItemDAO {
    @Insert
    Single<Long> insertGF(Item item);

    @Query("select * from item where id = :id")
    Single<Item> getItem(long id);

    @Insert
    Single<List<Long>> insertAll(List<Item> items);

    @Query("select * from item")
    Single<List<Item>> getAll();
}
