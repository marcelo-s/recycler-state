package com.example.recyclerstate.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.recyclerstate.R;

public class HomeFragment extends Fragment {

    private Button seeItemsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViewElements(view);
    }

    private void setViewElements(View view) {
        seeItemsButton = view.findViewById(R.id.button_see_items);
        setButtonListener();
    }

    private void setButtonListener() {
        seeItemsButton.setOnClickListener(this::goToItemsListListener);
    }

    private void goToItemsListListener(View view) {
        NavDirections toMasterListFragment = HomeFragmentDirections.actionHomeFragmentToListMasterFragment();
        Navigation.findNavController(view).navigate(toMasterListFragment);
    }
}