package com.example.haroldsmenu.service;

import com.example.haroldsmenu.models.MenuItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MenuService {

    public List<MenuItem> menuSelection() {
        // LocalTime time = LocalTime.now();

        // if (time.isBefore(LocalTime.NOON)) {

        // } else if ((time.equals(LocalTime.NOON) || time.isAfter(LocalTime.NOON))
        //        && time.isBefore(LocalTime.of(16, 0))) {

        // } else {
        return new ArrayList<>();
    }

    private List<MenuItem> getMorningMenu() {
        return new ArrayList<>();
    }

    private List<MenuItem> getRegularMenu() {
        return new ArrayList<>();
    }

    private List<MenuItem> getEveningMenu() {
        return new ArrayList<>();
    }
}
