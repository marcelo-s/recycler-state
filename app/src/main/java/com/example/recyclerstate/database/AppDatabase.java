package com.example.recyclerstate.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recyclerstate.database.Dao.IItemDAO;
import com.example.recyclerstate.viewmodel.Entity.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "items.db";
    public static final String PREPACKAGED_DATABASE_FILE_PATH = "database/items.db";
    private static volatile AppDatabase INSTANCE;

    public abstract IItemDAO itemDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .createFromAsset(PREPACKAGED_DATABASE_FILE_PATH)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
