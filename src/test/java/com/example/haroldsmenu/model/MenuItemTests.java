package com.example.haroldsmenu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

public class MenuItemTests {

    @Test
    void createMenuItem() {
        MenuItem item = new MenuItem();
        item.setName("Burger");
        assert(item.getName().equals("Burger"));
    }

    @Test
    void updateMenuItem() {
        MenuItem item = new MenuItem();
        assert(item.getName().equals("Cheeseburger"));
    }

    @Test
    void testMenuItemAvailability() {
        MenuItem item = new MenuItem("Burger", "Delicious beef burger", 5.99);
        item.setAvailable(true);
        assert(item.isAvailable() == true);
        item.setAvailable(false);
        assert(item.isAvailable() == false);
    }

    @Test
    void testMenuItemAllergens() {
        MenuItem item = new MenuItem("Salad", "Fresh garden salad", 4.99);
        item.setAllergens(List.of("Nuts", "Dairy"));
        assert(item.getAllergens().size() == 2);
        assert(item.getAllergens().contains("Nuts"));
        assert(item.getAllergens().contains("Dairy"));
    }


}
