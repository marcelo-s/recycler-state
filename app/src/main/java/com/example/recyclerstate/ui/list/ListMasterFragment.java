package com.example.recyclerstate.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerstate.R;
import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.ItemsViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListMasterFragment extends Fragment implements ItemsRecyclerViewAdapter.IItemRecyclerViewOwner {

    private ItemsViewModelImpl itemsViewModel;
    private List<Item> itemsData;
    private ItemsRecyclerViewAdapter itemsRecyclerViewAdapter;
    private ProgressBar loadingSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setItemsViewModel();
        setItemsData();
        setViewElements(view);
    }

    private void setItemsViewModel() {
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModelImpl.class);
    }

    private void setViewElements(View view) {
        setLoadingSpinner(view);
        setItemsRecyclerView(view);
    }

    private void setLoadingSpinner(View view) {
        loadingSpinner = view.findViewById(R.id.progressBar_master_loading);
    }

    private void setItemsRecyclerView(View view) {
        RecyclerView itemsRecyclerView = view.findViewById(R.id.recyclerView_items);
        itemsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(requireActivity());
        itemsRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        itemsRecyclerViewAdapter = new ItemsRecyclerViewAdapter(this, itemsData);
        itemsRecyclerView.setAdapter(itemsRecyclerViewAdapter);
    }

    private void setItemsData() {
        this.itemsData = new ArrayList<>();
        itemsViewModel.getItems().observe(getViewLifecycleOwner(), this::onItemsDataChange);
    }

    private void onItemsDataChange(List<Item> newItemsData) {
        updateRecyclerView(newItemsData);
        hideLoadingProgressBar();
    }

    private void hideLoadingProgressBar() {
        loadingSpinner.setVisibility(View.GONE);
    }

    private void updateRecyclerView(List<Item> newItemsData) {
        this.itemsData.clear();
        this.itemsData.addAll(newItemsData);
        itemsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View.OnClickListener seeItemButtonListener(ItemsRecyclerViewAdapter.ItemViewHolder holder, Item item) {
        return view -> goToItemDetail(view, item);
    }

    private void goToItemDetail(View view, Item item) {
        itemsViewModel.loadItem(item.getId());
        NavDirections toItemDetail = ListMasterFragmentDirections.actionListMasterFragmentToListDetailFragment();
        Navigation.findNavController(view).navigate(toItemDetail);
    }
}