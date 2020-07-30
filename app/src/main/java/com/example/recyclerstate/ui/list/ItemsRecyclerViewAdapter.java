package com.example.recyclerstate.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerstate.R;
import com.example.recyclerstate.viewmodel.Entity.Item;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ItemViewHolder> {

    public interface IItemRecyclerViewOwner {
        View.OnClickListener seeItemButtonListener(ItemViewHolder holder, Item item);
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        Button seeItemButton;

        private ItemViewHolder(View cardView) {
            super(cardView);
            name = cardView.findViewById(R.id.textView_name);
            description = cardView.findViewById(R.id.textView_item_detail_description);
            seeItemButton = cardView.findViewById(R.id.button_see_item);
        }
    }

    private final IItemRecyclerViewOwner ownerFragment;
    private final List<Item> itemsData;

    public ItemsRecyclerViewAdapter(IItemRecyclerViewOwner ownerFragment, List<Item> itemsData) {
        this.ownerFragment = ownerFragment;
        this.itemsData = itemsData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_view_item, parent, false);
        return new ItemViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Item item = itemsData.get(position);
        setViewElementsInHolder(holder, item);
        setButtonListener(holder, item);
    }

    private void setButtonListener(ItemViewHolder holder, Item item) {
        holder.getSeeItemButton().setOnClickListener(ownerFragment.seeItemButtonListener(holder, item));
    }

    private void setViewElementsInHolder(ItemViewHolder holder, Item item) {
        holder.getName().setText(item.getName());
        holder.getDescription().setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
