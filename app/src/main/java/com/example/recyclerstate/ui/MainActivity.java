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

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewElements();
        initializeViewModel();
    }

    private void initializeViewModel() {
        new ViewModelProvider(this).get(ItemsViewModelImpl.class);
    }

    private void setViewElements() {
        setContentView(R.layout.activity_main);
        setNavController();
        setToolbar();
    }

    private void setNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    private void setToolbar() {
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        NavigationUI.setupWithNavController(
                toolbar,
                navController,
                appBarConfiguration);
    }
}