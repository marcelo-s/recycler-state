package com.example.recyclerstate.ui.list;

import com.example.recyclerstate.viewmodel.state.ItemDetailErrorState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadedState;
import com.example.recyclerstate.viewmodel.state.ItemDetailLoadingState;

public interface ItemDetailVisitor {
    void handleItemDetailLoadingState(ItemDetailLoadingState itemDetailLoadingState);

    void handleItemDetailLoadedState(ItemDetailLoadedState itemDetailLoadedState);

    void handleItemDetailErrorState(ItemDetailErrorState itemDetailErrorState);
}
