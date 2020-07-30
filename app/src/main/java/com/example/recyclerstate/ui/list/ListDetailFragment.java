package com.example.recyclerstate.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recyclerstate.R;
import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.viewmodel.IItemsViewModel;
import com.example.recyclerstate.viewmodel.ItemsViewModelImpl;
import com.example.recyclerstate.viewmodel.state.ItemDetailErrorState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadedState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadingState;
import com.example.recyclerstate.viewmodel.state.ItemDetailState;
import com.google.android.material.snackbar.Snackbar;

public class ListDetailFragment extends Fragment implements ItemDetailVisitor {

    private IItemsViewModel itemsViewModel;
    private Item item;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ProgressBar progressBarLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setGFViewModel();
        setItemData();
        setViewElements(view);
    }

    private void setGFViewModel() {
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModelImpl.class);
    }

    private void setItemData() {
        itemsViewModel.getItemDetailState().observe(getViewLifecycleOwner(), this::onItemDetailStateChange);
    }

    private void setViewElements(View view) {
        nameTextView = view.findViewById(R.id.textView_item_detail_name);
        descriptionTextView = view.findViewById(R.id.textView_item_detail_description);
        progressBarLoading = view.findViewById(R.id.progressBar_detail);
    }

    private void onItemDetailStateChange(ItemDetailState itemDetailState) {
        itemDetailState.accept(this);
    }

    @Override
    public void handleItemDetailLoadingState(ItemDetailLoadingState itemDetailLoadingState) {
        showProgressBarLoading();
    }

    @Override
    public void handleItemDetailLoadedState(ItemDetailLoadedState itemDetailLoadedState) {
        setItemDetailData(itemDetailLoadedState);
        setItemDataOnView();
        hideLoadingProgressBar();
        Snackbar.make(requireView(), R.string.toast_loaded_successfully, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleItemDetailErrorState(ItemDetailErrorState itemDetailErrorState) {
        hideLoadingProgressBar();
        Toast.makeText(getActivity(), R.string.toast_error_loading_item, Toast.LENGTH_SHORT).show();
    }

    private void setItemDetailData(ItemDetailLoadedState itemDetailState) {
        this.item = itemDetailState.getItem();
    }

    private void setItemDataOnView() {
        nameTextView.setText(item.getName());
        descriptionTextView.setText(item.getDescription());
    }

    private void hideLoadingProgressBar() {
        progressBarLoading.setVisibility(View.GONE);
    }

    private void showProgressBarLoading() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

}