package com.example.haroldsmenu.store;

import org.junit.jupiter.api.Test;
import com.example.haroldsmenu.store.ItemStore;

public class MenuStoreTests {

    @Test
    void fileFound() {
        ItemStore store = new ItemStore();
        assert(store.getAllItems().size() > 0);
    }

}
