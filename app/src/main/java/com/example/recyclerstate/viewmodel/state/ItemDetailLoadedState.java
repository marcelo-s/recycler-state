package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;
import com.example.recyclerstate.viewmodel.Entity.Item;


public class ItemDetailLoadedState implements IItemDetailState {

    private final Item item;

    public static ItemDetailLoadedState of(Item item) {
        return new ItemDetailLoadedState(item);
    }

    private ItemDetailLoadedState(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailLoadedState(this);
    }

}
