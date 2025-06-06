package com.nutriia.nutriiaemf.models;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Model for TypicalDay
 * Contains the dishes for a typical day
 */
public class TypicalDay
{
    private final String name;

    private final List<Dish> dishes;

    public TypicalDay(String name, List<Dish> dishes)
    {
        this.name = name;
        this.dishes = dishes;
    }

    public String getName()
    {
        return name;
    }

    public List<Dish> getDishes()
    {
        return dishes;
    }

    public Set<String> getDishesStringSet()
    {
        Set<String> dishes = new HashSet<>();
        getDishes().forEach(dish -> dishes.add(dish.getName()));
        return dishes;
    }
}
