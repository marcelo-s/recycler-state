package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;


public class ItemDetailErrorState implements IItemDetailState {

    private static ItemDetailErrorState itemErrorState = new ItemDetailErrorState();

    public static ItemDetailErrorState getInstance() {
        return itemErrorState;
    }


    private ItemDetailErrorState(){}

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailErrorState(this);
    }
}
