package com.example.haroldsmenu.controller;

import com.example.haroldsmenu.model.MenuItem;
import com.example.haroldsmenu.store.ItemStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {

    private final ItemStore store;

    public MenuController(ItemStore store) {
        this.store = store;
    }

    @GetMapping("/menu")
    @Tag(name = "User", description = "Actions accessible to all users")
    public List<MenuItem> getMenu() {
        return new ArrayList<>();
    }

    @GetMapping("/menu/{id}")
    @Tag(name = "User", description = "Actions accessible to all users")
    public MenuItem getMenuItem(@PathVariable String id) {
        return new MenuItem();
    }

    @PostMapping("/admin/menu-items")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public MenuItem addItem(@RequestBody MenuItem item) {
        return new MenuItem();
    }

    @PutMapping("/admin/menu-items/{id}")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public MenuItem updateItem(@PathVariable String id, @RequestBody @Valid MenuItem item) {
        return new MenuItem();
    }

    @DeleteMapping("/admin/menu-items/{id}")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public void deleteItem(@PathVariable String id) {
    }

    private record Filters(){
    }

    private boolean matches(MenuItem item, Filters filters) {
        return true;
    }
}
