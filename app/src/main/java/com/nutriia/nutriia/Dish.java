package com.nutriia.nutriia;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    public static List<Dish> listToDishes(List<String> dishes) {
        List<Dish> dishList = new ArrayList<>();
        for (String dish : dishes) {
            dishList.add(new Dish(dish));
        }
        return dishList;
    }
    private final String name;

    public Dish(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
