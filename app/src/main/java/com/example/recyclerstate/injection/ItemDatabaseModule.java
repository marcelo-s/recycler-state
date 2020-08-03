package com.example.recyclerstate.injection;

import android.app.Application;

import com.example.recyclerstate.database.AppDatabase;
import com.example.recyclerstate.database.Dao.IItemDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn({ApplicationComponent.class})
public class ItemDatabaseModule {

    @Singleton
    @Provides
    public AppDatabase provideDatabase(Application application) {
        return AppDatabase.getInstance(application) ;
    }

    @Singleton
    @Provides
    public IItemDAO provideItemDAO(AppDatabase appDatabase) {
        return appDatabase.itemDAO();
    }

}
