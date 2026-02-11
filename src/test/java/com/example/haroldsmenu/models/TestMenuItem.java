package com.example.haroldsmenu.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMenuItem {

    private MenuItem newItem(String name) {
        MenuItem item = new MenuItem();

        List<String> allergens = new ArrayList<>();
        allergens.add("test");

        item.setName(name);
        item.setPrice(3.40);
        item.setAvailability(true);
        item.setAllergens(allergens);
        item.setVegetarian(false);
        item.setVegan(false);
        item.setCalories(500);

        return item;
    }

    @Test
    void TestNewItem() {
        MenuItem item = newItem("Quadruple Haroldburger");

        List<String> allergens = new ArrayList<>();
        allergens.add("test");

        assertAll(
                () -> assertEquals("Quadruple Haroldburger",item.getName()),
                () -> assertEquals(3.40,item.getPrice()),
                () -> assertTrue(item.isAvailable()),
                () -> assertEquals(allergens,item.getAllergens()),
                () -> assertFalse(item.isVegetarian()),
                () -> assertFalse(item.isVegan()),
                () -> assertEquals(500,item.getCalories())
        );
    }
}
