package com.example.recyclerstate.viewmodel.state;

import com.example.recyclerstate.ui.list.ItemDetailVisitor;

public interface IItemDetailState {
    void accept(ItemDetailVisitor itemDetailVisitor);
}
