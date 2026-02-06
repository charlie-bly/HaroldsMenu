package com.example.haroldsmenu.stores;

import com.example.haroldsmenu.models.MenuItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.util.*;

@Component
public class ItemStore {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final Path file;
    private final ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public ItemStore(@Value("${menu.storage.path:./data/menu.json}") String path) {
        this.file = Paths.get(path).toAbsolutePath().normalize();
    }

    public List<MenuItem> getAll() {
        return new ArrayList<>();
    }

    public MenuItem add(MenuItem item) {
        return new MenuItem();
    }

    public MenuItem update(String name, MenuItem patch) {
        return new MenuItem();
    }

    public void setAvailability(String name, boolean available) {
    }


    public void delete(String name) {
    }

    private void saveToFile() {
    }
}