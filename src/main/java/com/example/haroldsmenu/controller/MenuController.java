package com.example.haroldsmenu.controller;

import com.example.haroldsmenu.models.MenuItem;
import com.example.haroldsmenu.stores.ItemStore;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final ItemStore store;

    public MenuController(ItemStore store) {
        this.store = store;
    }

    @GetMapping("/menu")
    @Tag(name = "User", description = "Actions accessible to all users")
    public List<MenuItem> getMenu(
            @RequestParam Optional<Boolean> vegetarian,
            @RequestParam Optional<Boolean> vegan,
            @RequestParam Optional<List<String>> excludeAllergens,
            @RequestParam Optional<Double> maxPrice,
            @RequestParam Optional<Integer> maxCalories,
            @RequestParam Optional<Boolean> availableOnly
    ) {
        Filters filters = new Filters(
                vegetarian, vegan,
                availableOnly,
                maxPrice, maxCalories,
                excludeAllergens
        );

        log.info("Received request for menu with filters: {}", filters);
        List<MenuItem> results = new ArrayList<>();

        for (MenuItem item : store.getAll()) {
            if (matches(item, filters)) {
                log.debug("Item matches filters: {}", item.getName());
                results.add(item);
            } else {
                log.debug("Item does not match filters: {}", item.getName());
            }
        }

        log.info("{} items found matching filters", results.size());

        return results;
    }

    @GetMapping("/menu/{name}")
    @Tag(name = "User", description = "Actions accessible to all users")
    public MenuItem getMenuItem(@PathVariable String name) {
        log.info("Received getMenuItem request for item: {}", name);

        for (MenuItem item : store.getAll()) {
            if (item.getName().equalsIgnoreCase(name)) {
                log.debug("Found item: {}", item.getName());
                return item;
            }
        }

        log.error("Menu item not found: {}", name);
        return null;
    }

    @PostMapping("/admin/menu")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public MenuItem addItem(@RequestBody MenuItem item) {
        log.info("Received addItem request: {}", item.getName());
        return store.add(item);
    }

    @PutMapping("admin/menu/{name}/availability")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public void updateItem(@PathVariable String name, @RequestParam boolean available) {
        log.info("Received updateItem request: '{}' to {}", name, available);
        store.update(name, available);
        log.debug("Successfully updated availability");
    }

    //@PutMapping("/admin/menu/{name}")
    //@Tag(name = "Admin", description = "Actions accessible only to administrators")
    //public MenuItem updateItem(@PathVariable String name, @RequestBody @Valid MenuItem item) {
    //    log.info("Received request to update menu item '{}' with data: {}", name, item);
    //    return store.update(name,item);
    //}

    @DeleteMapping("/admin/menu/{name}")
    @Tag(name = "Admin", description = "Actions accessible only to administrators")
    public void deleteItem(@PathVariable String name) {
        log.info("Received request to delete menu item: {}", name);
        store.delete(name);
        log.debug("Successfully deleted item: {}", name);
    }

    private record Filters(
            Optional<Boolean> vegetarian,
            Optional<Boolean> vegan,
            Optional<Boolean> availableOnly,
            Optional<Double> maxPrice,
            Optional<Integer> maxCalories,
            Optional<List<String>> excludeAllergens
    ) {}

    private boolean matches(MenuItem item, Filters filters) {
        List<String> allergens = item.getAllergens();
        log.info("Checking filter matches for item: {}", item.getName());

        if (filters.vegetarian.isPresent() && item.isVegetarian() != filters.vegetarian().get()) {
            log.debug("Rejected by vegetarian filter (value: {})", item.isVegetarian());
            return false;
        } else if (filters.vegetarian.isPresent()) {
            log.debug("Accepted by vegetarian filter (value: {})", item.isVegetarian());
        }

        if (filters.vegan.isPresent() && item.isVegan() != filters.vegan().get()) {
            log.debug("Rejected by vegan filter (value: {})", item.isVegan());
            return false;
        } else if (filters.vegan.isPresent()) {
            log.debug("Accepted by vegan filter (value: {})", item.isVegan());
        }

        if (filters.availableOnly.orElse(false) && !item.isAvailable()) {
            log.debug("Rejected by availableOnly filter (value: {})", item.isAvailable());
            return false;
        } else if (filters.availableOnly.orElse(false)) {
            log.debug("Accepted by availableOnly filter (value: {})", item.isAvailable());
        }

        if (filters.maxPrice.isPresent() && item.getPrice() > filters.maxPrice.get()) {
            log.debug("Rejected by maxPrice filter (value: {})", item.getPrice());
            return false;
        } else if (filters.maxPrice.isPresent()) {
            log.debug("Accepted by maxPrice filter (value: {})", item.getPrice());
        }

        if (filters.maxCalories.isPresent() && item.getCalories() > 1000) {
            log.debug("Rejected by maxCalories filter (value: {}, limit: 1000)", item.getCalories());
            return false;
        } else if (filters.maxCalories.isPresent()) {
            log.debug("Accepted by maxCalories filter (value: {}, limit: 1000)", item.getCalories());
        }
        if (filters.excludeAllergens.isPresent() & allergens != null) {
            for (String allergen : filters.excludeAllergens().get()) {
                if (item.getAllergens().contains(allergen)) {
                    log.debug("Rejected by excludeAllergens filter (value: {}, blocked: {})",
                            allergens, filters.excludeAllergens.get());
                    return false;
                }
            }
            log.debug("Accepted by excludeAllergens filter (value: {}, blocked: {},)",
                    allergens, filters.excludeAllergens().get());
        }
        return true;
    }
}