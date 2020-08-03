package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;

public class ItemDetailLoadingState implements IItemDetailState {

    private static ItemDetailLoadingState itemDetailLoadingState = new ItemDetailLoadingState();

    public static ItemDetailLoadingState getInstance() {
        return ItemDetailLoadingState.itemDetailLoadingState;
    }

    private ItemDetailLoadingState() {
    }

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailLoadingState(this);
    }

}
