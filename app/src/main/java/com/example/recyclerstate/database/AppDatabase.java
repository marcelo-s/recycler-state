package com.example.recyclerstate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recyclerstate.database.Dao.IItemDAO;
import com.example.recyclerstate.viewmodel.Entity.Item;

import java.util.concurrent.Executors;

import io.reactivex.disposables.Disposable;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "items.db";
    public static final String DATABASE_FILE_PATH = "database/items.db";
    private static volatile AppDatabase INSTANCE;
    private static final String TAG = "AppDatabase";
    private static Disposable initialInsertionDisposable;

    public abstract IItemDAO itemDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .createFromAsset(DATABASE_FILE_PATH)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback populateInitialDataCallback(Runnable onCreateCallback) {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Executors.newSingleThreadScheduledExecutor().execute(onCreateCallback);
            }
        };
    }
}
