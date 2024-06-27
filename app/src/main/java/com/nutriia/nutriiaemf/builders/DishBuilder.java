package com.nutriia.nutriiaemf.builders;

import com.nutriia.nutriiaemf.models.Dish;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DishBuilder {
    public static List<Dish> buildDish(UserSharedPreferences userSharedPreferences) {
        Set<String> dishes = userSharedPreferences.getDishSuggestions();
        List<Dish> dishList = new ArrayList<>();
        for (String dish : dishes) {
            dishList.add(new Dish(dish));
        }
        return dishList;
    }
}
