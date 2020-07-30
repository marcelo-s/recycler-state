package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDetailLoadingState implements ItemDetailState {

    private static ItemDetailLoadingState itemDetailLoadingState = new ItemDetailLoadingState();

    public static ItemDetailLoadingState getInstance() {
        return ItemDetailLoadingState.itemDetailLoadingState;
    }

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailLoadingState(this);
    }

}
