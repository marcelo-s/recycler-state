package com.example.recyclerstate.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.recyclerstate.R;
import com.example.recyclerstate.viewmodel.ItemsViewModelImpl;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewModel();
        setViewElements();
    }

    private void initializeViewModel() {
        new ViewModelProvider(this).get(ItemsViewModelImpl.class);
    }

    private void setViewElements() {
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        toolbar = findViewById(R.id.main_toolbar);
        configureToolBarWithNavConfig();
    }


    private void configureToolBarWithNavConfig() {
        AppBarConfiguration appBarConfiguration =
                getAppBarConfiguration();
        setSupportActionBar(toolbar);
        NavigationUI.setupWithNavController(
                toolbar,
                navController,
                appBarConfiguration);
    }

    @NotNull
    private AppBarConfiguration getAppBarConfiguration() {
        return new AppBarConfiguration.Builder(navController.getGraph()).build();
    }
}