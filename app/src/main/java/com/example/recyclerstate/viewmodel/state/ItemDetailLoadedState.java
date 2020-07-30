package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.viewmodel.Entity.Item;
import com.example.recyclerstate.ui.list.ItemDetailVisitor;

import lombok.Value;

@Value(staticConstructor = "of")
public class ItemDetailLoadedState implements ItemDetailState {

    Item item;

    @Override
    public void accept(ItemDetailVisitor itemDetailVisitor) {
        itemDetailVisitor.handleItemDetailLoadedState(this);
    }

}
