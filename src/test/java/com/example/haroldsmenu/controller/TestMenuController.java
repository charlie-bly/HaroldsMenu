package com.example.haroldsmenu.controller;


import com.example.haroldsmenu.models.MenuItem;
import com.example.haroldsmenu.stores.ItemStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestMenuController {

    private boolean addPassed = false;
    private boolean getMenuPassed = false;
    private boolean getMenuItemPassed = false;

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
        item.setPrice(2.99);
        return item;
    }

    private List<MenuItem> newMenu() {
        List<MenuItem> menu = new ArrayList<>();
        menu.add(newItem("Quadruple Haroldburger"));
        menu.add(newItem("Chicken Spice Wrap"));
        return menu;
    }

    @Test
    void TestAddItem() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        List<MenuItem> menu = controller.getMenu();

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Quadruple Haroldburger", menu.getFirst().getName()),
                () -> assertTrue(getMenuPassed,"Used Method getMenu() failed")
        );

        addPassed = true;
    }

    @Test
    void TestGetMenu() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item1 = newItem("Quadruple Haroldburger");
        MenuItem item2 = newItem("Chicken Spice Wrap");

        controller.addItem(item1);
        controller.addItem(item2);

        List<MenuItem> menu = controller.getMenu();

        assertAll(
                () -> assertEquals(2, menu.size()),
                () -> assertEquals("Quadruple Haroldburger", menu.getFirst().getName()),
                () -> assertEquals("Chicken Spice Wrap", menu.get(1).getName()),
                () -> assertTrue(addPassed,"Used Method addItem() failed")
        );

        getMenuPassed = true;
    }

    @Test
    void TestGetMenuItem() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        MenuItem testItem = controller.getMenuItem("Quadruple Haroldburger");

        assertAll(
                () -> assertEquals("Quadruple Haroldburger", testItem.getName()),
                () -> assertTrue(addPassed,"Used Method addItem() failed"),
                () -> assertTrue(getMenuPassed,"Used Method getMenu() failed")
        );

        getMenuItemPassed = true;
    }

    @Test
    void TestUpdateItem() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        MenuItem updatedItem = newItem("Double Stack Sandwich");
        controller.updateItem("Quadruple Haroldburger", updatedItem);

        MenuItem testItem = controller.getMenuItem("Double Stack Sandwich");

        assertAll(
                () -> assertEquals("Double Stack Sandwich", testItem.getName()),
                () -> assertTrue(addPassed,"Used Method addItem() failed"),
                () -> assertTrue(getMenuItemPassed,"Used Method getMenuItem() failed")
        );
    }

    @Test
    void TestDeleteItem() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        controller.deleteItem("Quadruple Haroldburger");

        List<MenuItem> menu = controller.getMenu();

        assertAll(
                () -> assertEquals(0, menu.size()),
                () -> assertTrue(addPassed,"Used Method addItem() failed"),
                () -> assertTrue(getMenuPassed,"Used Method getMenu() failed")
        );
    }

    @Test
    void TestVegetarianFilter() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item1 = newItem("Quadruple Haroldburger");
        item1.setVegetarian(true);
        MenuItem item2 = newItem("Chicken Spice Wrap");
        item2.setVegetarian(false);

        controller.addItem(item1);
        controller.addItem(item2);

        List<MenuItem> vegetarianMenu = controller.getMenu();
        assertAll(
                () -> assertEquals(1, vegetarianMenu.size()),
                () -> assertEquals("Quadruple Haroldburger", vegetarianMenu.getFirst().getName())
        );
    }
}
