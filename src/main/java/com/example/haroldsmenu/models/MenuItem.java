package com.example.haroldsmenu.models;

import jakarta.validation.constraints.*;
import java.util.List;

public class MenuItem {

    @NotBlank
    private String name;

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
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public int getCalories() {
        return calories;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}