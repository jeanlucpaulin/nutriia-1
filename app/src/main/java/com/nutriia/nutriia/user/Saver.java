package com.nutriia.nutriia.user;

import android.content.Context;
import android.util.Log;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.utils.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Saver {
    public static Set<String> saveMRDInputBreakfast(Context context, String input) {
        Set<String> dishes = new HashSet<>(Arrays.asList(input.split("[\n,;.]")));
        Log.d("Saver", "saveMRDInputBreakfast: " + dishes.toString());
        UserSharedPreferences.getInstance(context).setMRDABreakfast(dishes);

        return dishes;
    }

    public static Set<String> saveMRDInputLunch(Context context, String input) {
        Set<String> dishes = new HashSet<>(Arrays.asList(input.split("[\n,;.]")));
        UserSharedPreferences.getInstance(context).setMRDALunch(dishes);

        return dishes;
    }

    public static Set<String> saveMRDInputDinner(Context context, String input) {
        Set<String> dishes = new HashSet<>(Arrays.asList(input.split("[\n,;.]")));
        UserSharedPreferences.getInstance(context).setMRDADinner(dishes);

        return dishes;
    }

    public static Set<String> saveMRDInputSnack(Context context, String input) {
        Set<String> dishes = new HashSet<>(Arrays.asList(input.split("[\n,;.]")));
        UserSharedPreferences.getInstance(context).setMRDASnack(dishes);

        return dishes;
    }

    public static void saveMyReadDay(Context context, Day day) {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(context);
        Nutrient calorie = day.getCalorie();
        userSharedPreferences.setMRDCalories(calorie.getProgress());
        Nutrient fiber = day.getFiber();
        userSharedPreferences.setMRDFibers(fiber.getProgress());
        for(Nutrient macronutrient : day.getMacroNutrients().values()) {
            userSharedPreferences.setMRDNutrient(macronutrient.getName(), macronutrient.getProgress());
        }
        for(Nutrient micronutrient : day.getMicroNutrients().values()) {
            userSharedPreferences.setMRDNutrient(micronutrient.getName(), micronutrient.getProgress());
        }

        String date = Date.getTodayDate();
        userSharedPreferences.setMRDDate(date);
    }

    public static void saveDishSuggestions(Context context, List<Dish> suggestions) {
        Set<String> suggestionsString = new HashSet<>();
        for(Dish dish : suggestions) {
            suggestionsString.add(dish.getName());
        }
        UserSharedPreferences.getInstance(context).setDishSuggestions(suggestionsString);

        UserSharedPreferences.getInstance(context).setDishSuggestionsDate(Date.getTodayDate());
    }
}
