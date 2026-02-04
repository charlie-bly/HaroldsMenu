package com.example.haroldsmenu.store;

import com.example.haroldsmenu.controller.MenuController;
import com.example.haroldsmenu.model.MenuItem;
import org.junit.jupiter.api.Test;
import com.example.haroldsmenu.store.ItemStore;
import com.example.haroldsmenu.controller.MenuController;
import org.springframework.web.bind.annotation.RequestParam;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuStoreTests {

    @Test
    void saveSuccess() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Path temp = Files.createTempFile("test-file",".json");
        Files.writeString(temp, "[]");

        MenuItem item = new MenuItem();
        item.setName("Test Item");

        List<MenuItem> items = mapper.readValue(temp.toFile(),
                new TypeReference<List<MenuItem>>() {});
        items.add(item);

        mapper.writeValue(temp.toFile(), item);

        List<MenuItem> reloaded = mapper.readValue(temp.toFile(),
                new TypeReference<List<MenuItem>>() {});

        if (reloaded.stream().anyMatch(tempItem -> tempItem.getName().equals("Test Item"))) {
            assert(true);
        } else {
            assert(false);
        }
    }


}
