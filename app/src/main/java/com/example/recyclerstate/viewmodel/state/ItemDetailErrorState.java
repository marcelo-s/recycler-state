package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDetailErrorState implements ItemDetailState {

    private static ItemDetailErrorState itemErrorState = new ItemDetailErrorState();

    public static ItemDetailErrorState getInstance() {
        return itemErrorState;
    }

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailErrorState(this);
    }
}
