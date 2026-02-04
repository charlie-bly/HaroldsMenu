package com.example.haroldsmenu.controller;

import com.example.haroldsmenu.model.MenuItem;
import com.example.haroldsmenu.store.ItemStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {

    private final ItemStore store;

    public MenuController(ItemStore store) {
    }

    public List<MenuItem> getMenu() {
    }

    public MenuItem addItem(@RequestBody MenuItem item) {
    }

    public MenuItem updateItem(@PathVariable String id, @RequestBody @Valid MenuItem item) {
    }

    public void deleteItem(@PathVariable String id) {
    }

    private record Filters(){
    }

    private static boolean matches(MenuItem item, Filters filters) {
    }
}
