package com.example.recyclerstate.injection;

import com.example.recyclerstate.database.Repository.ItemLocalRepository;
import com.example.recyclerstate.viewmodel.IItemRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class ItemRepositoryModule {

    @Singleton
    @Binds
    public abstract IItemRepository bindItemRepository(ItemLocalRepository itemLocalRepository);
}
