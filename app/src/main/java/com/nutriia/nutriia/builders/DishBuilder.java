package com.nutriia.nutriia.builders;

import com.nutriia.nutriia.models.Dish;
import com.nutriia.nutriia.user.UserSharedPreferences;

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
