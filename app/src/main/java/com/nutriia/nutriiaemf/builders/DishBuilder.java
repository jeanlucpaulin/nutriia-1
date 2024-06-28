package com.nutriia.nutriiaemf.builders;

import com.nutriia.nutriiaemf.models.Dish;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DishBuilder class
 * This class is used to build a Dish from a UserSharedPreferences object
 */
public class DishBuilder {

    /**
     * Build a list of Dish objects from a UserSharedPreferences object
     * @param userSharedPreferences UserSharedPreferences object
     * @return
     */
    public static List<Dish> buildDish(UserSharedPreferences userSharedPreferences) {
        Set<String> dishes = userSharedPreferences.getDishSuggestions();
        List<Dish> dishList = new ArrayList<>();
        for (String dish : dishes) {
            dishList.add(new Dish(dish));
        }
        return dishList;
    }
}
