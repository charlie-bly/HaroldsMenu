package com.example.haroldsmenu.store;

import com.example.haroldsmenu.model.MenuItem;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Component
public class ItemStore {

    private final ObjectMapper mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT);

    private final Path file;
    private final ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public ItemStore(@Value("${menu.storage.path:./data/menu.json}") String path) {
    }

    public synchronized List<MenuItem> getAll() {
    }

    public synchronized MenuItem add(MenuItem item) {
    }

    public synchronized MenuItem update(String id, MenuItem patch) {
    }

    public synchronized void setAvailability(String id, boolean available) {
    }

    public synchronized void delete(String id) {
    }

    private void saveToFile() {
    }
}
