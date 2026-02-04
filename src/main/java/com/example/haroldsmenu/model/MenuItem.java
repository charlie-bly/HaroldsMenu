package com.example.haroldsmenu.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class MenuItem {

    @NotBlank
    private String id;

    @NotNull @Min(0)
    private double price;

    @NotNull
    private boolean available;

    private List<String> allergens;
    private boolean vegetarian;
    private boolean vegan;

    @Min(0)
    private int calories;

    // Getters
    public String getName() {
    }

    public double getPrice() {
    }

    public boolean isAvailable() {
    }

    public List<String> getAllergens() {
    }

    public boolean isVegetarian() {
    }

    public boolean isVegan() {
    }

    public int getCalories() {
    }

    // Setters
    public void setName(String name) {
    }

    public void setPrice(BigDecimal price) {
    }

    public void setAvailable(boolean available) {
    }

    public void setAllergens(List<String> allergens) {
    }

    public void setVegetarian(boolean vegetarian) {
    }

    public void setVegan(boolean vegan) {
    }

    public void setCalories(int calories) {
    }
}
