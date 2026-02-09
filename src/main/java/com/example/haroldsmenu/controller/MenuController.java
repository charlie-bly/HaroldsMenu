package com.example.haroldsmenu.controller;

import com.example.haroldsmenu.models.MenuItem;
import com.example.haroldsmenu.stores.ItemStore;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MenuController {

    private final ItemStore store;

    public MenuController(ItemStore store) {
        this.store = store;
    }

    @GetMapping("/menu")
    @Tag(name = "User", description = "Actions accessible to all users")
    public List<MenuItem> getMenu(
            @RequestParam Optional<Boolean> vegetarian,
            @RequestParam Optional<Boolean> vegan,
            @RequestParam Optional<List<String>> includeAllergens,
            @RequestParam Optional<List<String>> excludeAllergens,
            @RequestParam Optional<BigDecimal> maxPrice,
            @RequestParam Optional<Integer> maxCalories,
            @RequestParam Optional<Boolean> availableOnly
    ) {
        Filters filters = new Filters(
                vegetarian, vegan,
                availableOnly,
                maxPrice, maxCalories,
                includeAllergens, excludeAllergens
        );

        return new ArrayList<>();
    }

    @GetMapping("/menu/{name}")
    @Tag(name = "User", description = "Actions accessible to all users")
    public MenuItem getMenuItem(@PathVariable String name) {
        return new MenuItem();
    }

    @PostMapping("/admin/menu")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public MenuItem addItem(@RequestBody MenuItem item) {
        return new MenuItem();
    }

    @PutMapping("/admin/menu/{name}")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public MenuItem updateItem(@PathVariable String name, @RequestBody @Valid MenuItem item) {
        return new MenuItem();
    }

    @DeleteMapping("/admin/menu/{name}")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public void deleteItem(@PathVariable String name) {
    }

    private record Filters(
            Optional<Boolean> vegetarian,
            Optional<Boolean> vegan,
            Optional<Boolean> availableOnly,
            Optional<BigDecimal> maxPrice,
            Optional<Integer> maxCalories,
            Optional<List<String>> includeAllergens,
            Optional<List<String>> excludeAllergens
    ) {}

    private boolean matches(MenuItem item, Filters filters) {
        if (filters.vegetarian.isPresent() && item.isVegetarian() != filters.vegetarian().get()) {
            return false;
        }
        if (filters.vegan.isPresent() && item.isVegan() != filters.vegan().get()) {
            return false;
        }
        if (filters.availableOnly.isPresent() && filters.availableOnly().get() && !item.isAvailable()) {
            return false;
        }
        if (filters.maxPrice.isPresent() && item.getPrice() > 0) {
            return false;
        }
        if (filters.maxCalories.isPresent() && item.getCalories() > filters.maxCalories().get()) {
            return false;
        }
        if (filters.includeAllergens.isPresent()) {
            for (String allergen : filters.includeAllergens().get()) {
                if (!item.getAllergens().contains(allergen)) {
                    return false;
                }
            }
        }
        if (filters.excludeAllergens.isPresent()) {
            for (String allergen : filters.excludeAllergens().get()) {
                if (item.getAllergens().contains(allergen)) {
                    return false;
                }
            }
        }
        return true;
    }
}