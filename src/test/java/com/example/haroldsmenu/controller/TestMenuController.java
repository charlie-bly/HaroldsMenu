package com.example.haroldsmenu.controller;


import com.example.haroldsmenu.models.MenuItem;
import com.example.haroldsmenu.stores.ItemStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestMenuController {

    private boolean addPassed = false;
    private boolean getMenuPassed = false;
    private boolean getMenuItemPassed = false;
    private ItemStore store;
    private MenuController controller;

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

    private ItemStore newMenu() {
        ItemStore store = tempStore(tempFile());
        store.add(newItem("Quadruple Haroldburger"));
        store.add(newItem("Chicken Spice Wrap"));
        return store;
    }

    @BeforeEach
    void setup() {
        controller = new MenuController(tempStore(tempFile()));
        store = tempStore(tempFile());
    }

    @AfterEach
    void reset() {
        tempStore(tempFile()).deleteAll();
    }

    @Test
    void TestAddItem() {
        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(), // vegetarian
                Optional.empty(), // vegan
                Optional.empty(), // excludeAllergens
                Optional.empty(), // maxPrice
                Optional.empty(), // maxCalories
                Optional.empty()  // availableOnly
        );

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Quadruple Haroldburger", menu.getFirst().getName()),
                () -> assertTrue(getMenuPassed,"Used Method getMenu() failed")
        );

        addPassed = true;
    }

    @Test
    void TestGetMenu() {
        MenuItem item1 = newItem("Quadruple Haroldburger");
        MenuItem item2 = newItem("Chicken Spice Wrap");

        controller.addItem(item1);
        controller.addItem(item2);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(), // vegetarian
                Optional.empty(), // vegan
                Optional.empty(), // excludeAllergens
                Optional.empty(), // maxPrice
                Optional.empty(), // maxCalories
                Optional.empty()  // availableOnly
        );

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
        MenuItem item = newItem("Quadruple Haroldburger");
        item.setAvailability(false);
        controller.addItem(item);

        controller.updateItem("Quadruple Haroldburger", true);

        MenuItem testItem = controller.getMenuItem("Double Stack Sandwich");

        assertAll(
                () -> assertEquals("Double Stack Sandwich", testItem.getName()),
                () -> assertTrue(addPassed,"Used Method addItem() failed"),
                () -> assertTrue(getMenuItemPassed,"Used Method getMenuItem() failed")
        );
    }

    @Test
    void TestDeleteItem() {
        MenuItem item = newItem("Quadruple Haroldburger");
        controller.addItem(item);

        controller.deleteItem("Quadruple Haroldburger");

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(), // vegetarian
                Optional.empty(), // vegan
                Optional.empty(), // excludeAllergens
                Optional.empty(), // maxPrice
                Optional.empty(), // maxCalories
                Optional.empty()  // availableOnly
        );

        assertAll(
                () -> assertEquals(0, menu.size()),
                () -> assertTrue(addPassed,"Used Method addItem() failed"),
                () -> assertTrue(getMenuPassed,"Used Method getMenu() failed")
        );
    }

    @Test
    void TestVegetarianFilter() {
        MenuItem item1 = newItem("Quadruple Haroldburger");
        item1.setVegetarian(true);
        MenuItem item2 = newItem("Chicken Spice Wrap");
        item2.setVegetarian(false);

        controller.addItem(item1);
        controller.addItem(item2);

        //List<MenuItem> vegetarianMenu = controller;
        List<MenuItem> vegetarianMenu = controller.getMenu(
                Optional.of(true), // vegetarian
                Optional.empty(), // vegan
                Optional.empty(), // excludeAllergens
                Optional.empty(), // maxPrice
                Optional.empty(), // maxCalories
                Optional.empty()  // availableOnly
        );

        assertAll(
                () -> assertEquals(1, vegetarianMenu.size()),
                () -> assertEquals("Quadruple Haroldburger", vegetarianMenu.getFirst().getName())
        );
    }

    @Test
    void TestVeganFilter() {
        MenuItem item1 = newItem("Quadruple Haroldburger");
        item1.setVegan(true);
        MenuItem item2 = newItem("Chicken Spice Wrap");
        item2.setVegan(false);

        controller.addItem(item1);
        controller.addItem(item2);

        List<MenuItem> veganMenu = controller.getMenu(
                Optional.empty(), // vegetarian
                Optional.of(true), // vegan
                Optional.empty(), // excludeAllergens
                Optional.empty(), // maxPrice
                Optional.empty(), // maxCalories
                Optional.empty()  // availableOnly
        );

        assertAll(
                () -> assertEquals(1, veganMenu.size()),
                () -> assertEquals("Quadruple Haroldburger", veganMenu.getFirst().getName())
        );
    }

    @Test
    void TestExcludeAllergensFilter() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem item1 = newItem("Peanut Satay Wrap");
        item1.setAllergens(List.of("peanuts"));

        MenuItem item2 = newItem("Plain Salad");
        item2.setAllergens(List.of());

        controller.addItem(item1);
        controller.addItem(item2);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),      // vegetarian
                Optional.empty(),      // vegan
                Optional.of(List.of("peanuts")), // excludeAllergens
                Optional.empty(),      // maxPrice
                Optional.empty(),      // maxCalories
                Optional.empty()       // availableOnly
        );

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Plain Salad", menu.getFirst().getName())
        );
    }

    @Test
    void TestMaxPriceFilter() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem cheap = newItem("Budget Burger");
        cheap.setPrice(4.99);

        MenuItem expensive = newItem("Luxury Lobster Roll");
        expensive.setPrice(19.99);

        controller.addItem(cheap);
        controller.addItem(expensive);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),      // vegetarian
                Optional.empty(),      // vegan
                Optional.empty(),      // excludeAllergens
                Optional.of(10.00),    // maxPrice
                Optional.empty(),      // maxCalories
                Optional.empty()       // availableOnly
        );

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Budget Burger", menu.getFirst().getName())
        );
    }

    @Test
    void TestMaxCaloriesFilter() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem lowCal = newItem("Light Salad");
        lowCal.setCalories(250);

        MenuItem highCal = newItem("Mega Melt Sandwich");
        highCal.setCalories(1200);

        controller.addItem(lowCal);
        controller.addItem(highCal);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),      // vegetarian
                Optional.empty(),      // vegan
                Optional.empty(),      // excludeAllergens
                Optional.empty(),      // maxPrice
                Optional.of(1000),      // maxCalories
                Optional.empty()       // availableOnly
        );

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Light Salad", menu.getFirst().getName())
        );
    }

    @Test
    void TestAvailableOnlyFilter() {
        ItemStore store = tempStore(tempFile());
        MenuController controller = new MenuController(store);

        MenuItem available = newItem("Daily Special");
        available.setAvailability(true);

        MenuItem unavailable = newItem("Seasonal Pie");
        unavailable.setAvailability(false);

        controller.addItem(available);
        controller.addItem(unavailable);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),      // vegetarian
                Optional.empty(),      // vegan
                Optional.empty(),      // excludeAllergens
                Optional.empty(),      // maxPrice
                Optional.empty(),      // maxCalories
                Optional.of(true)      // availableOnly
        );

        assertAll(
                () -> assertEquals(1, menu.size()),
                () -> assertEquals("Daily Special", menu.getFirst().getName())
        );
    }

    @Test
    void TestGetMenuItemNotFound() {
        MenuItem item = controller.getMenuItem("Nonexistent Item");
        assertNull(item);
    }

    @Test
    void TestUpdateItem_NotFound() {
        controller.updateItem("Nonexistent Item", true);
        MenuItem result = controller.getMenuItem("Nonexistent Item");

        assertNull(result, "Updating a nonexistent item should not create it");
    }

    @Test
    void TestDeleteItem_NotFound() {
        controller.deleteItem("Ghost Item");

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        assertEquals(0, menu.size(),
                "Deleting nonexistent item should not affect menu");
    }

    @Test
    void TestMaxPriceFilter_BugRejectsAllItems() {
        MenuItem cheap = newItem("Cheap Item");
        cheap.setPrice(4.99);

        controller.addItem(cheap);

        List<MenuItem> menu = controller.getMenu(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(10.0),   // should allow item
                Optional.empty(),
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(0, menu.size(), "Bug: maxPrice filter rejects all items with price > 0")
        );
    }


}
