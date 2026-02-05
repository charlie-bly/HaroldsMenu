package com.example.haroldsmenu.store;

import com.example.haroldsmenu.model.MenuItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ItemStoreTest {

    @TempDir
    Path tempDir;

    private Path tempFile() {
        return tempDir.resolve("test-menu.json");
    }

//    private ItemStore tempStore(Path file) {
//        return new ItemStore(file.toString());
//    }

    private MenuItem newItem(String name) {
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setAvailable(true);
        item.setVegetarian(false);
        item.setVegan(false);
        item.setCalories(500);
        return item;
    }

    // TESTS
    @Test
    public void additionOrderTest() {
        ItemStore store = new ItemStore(tempFile().toString());
        store.add(newItem("Chicken Spice Wrap"));
        store.add(newItem("Double Stack Sandwich"));

        List<MenuItem> updatedStore = store.getAll();
        System.out.println(updatedStore);

        assertAll(
                () -> assertEquals(2,updatedStore.size()),
                () -> assertEquals("Chicken Spice Wrap",updatedStore.getFirst().getName()),
                () -> assertEquals("Double Stack Sandwich",updatedStore.get(1).getName())
        );
    }

    @Test
    void getA() {
    }
}
