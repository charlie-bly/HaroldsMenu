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
}
