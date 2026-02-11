package com.example.haroldsmenu.stores;

import com.example.haroldsmenu.controller.MenuController;
import com.example.haroldsmenu.models.MenuItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Component
public class ItemStore {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final Path file;
    private final ArrayList<MenuItem> items = new ArrayList<>();

    public ItemStore(@Value("${menu.storage.path:./data/menu.json}") String path) {
        this.file = Paths.get(path).toAbsolutePath().normalize();
    }

    public List<MenuItem> getAll() {
        log.info("Retrieving all items, total count: {}", items.size());
        return new ArrayList<>(items);
    }

    public MenuItem add(MenuItem item) {
        items.add(item);
        saveToFile();
        return item;
    }

    public MenuItem update(String name, boolean available) {
        for (MenuItem item : items) {
            if (item.getName().equals(name)) {
                item.setAvailability(available);
                saveToFile();
                return item;
            } else {
                log.error("Item '{}' not found", name);
            }
        }
        return null;
    }

    public void delete(String name) {
    }

    public void deleteAll() {
        items.clear();
        saveToFile();
    }

    private List<MenuItem> readFromFile() {
        return new ArrayList<>();
    }

    private void saveToFile() {
    }
}