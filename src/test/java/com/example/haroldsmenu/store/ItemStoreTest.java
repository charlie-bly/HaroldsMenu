package com.example.haroldsmenu.store;

import com.example.haroldsmenu.model.MenuItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;
import java.util.List;

public class ItemStoreTest {

    @TempDir
    Path tempDir;

    private Path tempFile() {
        return tempDir.resolve("test-menu.json");
    }

    private ItemStore tempStore(Path file) {
        return new ItemStore(file.toString());
    }

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

    // Currently fails because add() and getAll() have no functionality yet
    @Test
    void TestAdditionAndGetAll() {
        ItemStore store = tempStore(tempFile());
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
    void TestUpdate() {
        ItemStore store = tempStore(tempFile());
        store.add(newItem("Chicken Spice Wrap"));

        store.update("Chicken Spice Wrap,", newItem("Chicken Caesar Wrap"));
        List<MenuItem> updatedStore = store.getAll();

        assertAll(
                () -> assertEquals(1,updatedStore.size()),
                () -> assertEquals("Chicken Caesar Wrap",updatedStore.getFirst().getName())
        );
    }

    @Test
    void TestAvailability() {
        ItemStore store = tempStore(tempFile());
        store.add(newItem("Chicken Spice Wrap"));

        store.setAvailability("Chicken Spice Wrap", false);
        List<MenuItem> updatedStore = store.getAll();

        assertAll(
                () -> assertEquals(1,updatedStore.size()),
                () -> assertFalse(updatedStore.getFirst().isAvailable())
        );
    }

    @Test
    void TestDelete() {
        ItemStore store = tempStore(tempFile());
        store.add(newItem("Chicken Spice Wrap"));

        store.delete("Chicken Spice Wrap");
        List<MenuItem> updatedStore = store.getAll();
        assertEquals(0,updatedStore.size());
    }

    @Test
    void TestPersistence() {
        ItemStore store = tempStore(tempFile());
        store.add(newItem("Chicken Spice Wrap"));

        // Creating a new store instance, reading from the same file
        ItemStore newStore = tempStore(tempFile());
        List<MenuItem> items = newStore.getAll();

        assertAll(
                () -> assertEquals(1,items.size()),
                () -> assertEquals("Chicken Spice Wrap",items.getFirst().getName())
        );
    }
}

