package com.nutriia.nutriiaemf.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private final String name;
    private final String recipe;
    private final int calories;
    private final JSONObject micronutrients;
    private final JSONObject macronutrients;

    public Dish(JSONObject jsonObject) {
        String tempName = "";
        String tempRecipe = "";
        int tempCalories = 0;
        JSONObject tempMicronutrients = null;
        JSONObject tempMacronutrients = null;

        try {
            tempName = jsonObject.getString("name");
            tempRecipe = jsonObject.getString("recipe");

            JSONObject compositionObject = jsonObject.getJSONObject("composition");
            JSONObject caloriesObject = compositionObject.getJSONObject("calories");
            tempCalories = caloriesObject.getInt("value");

            tempMicronutrients = compositionObject.getJSONObject("micronutrients");
            tempMacronutrients = compositionObject.getJSONObject("macronutrients");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.name = tempName;
        this.recipe = tempRecipe;
        this.calories = tempCalories;
        this.micronutrients = tempMicronutrients;
        this.macronutrients = tempMacronutrients;
    }

    public Dish(String name) {
        this.name = name;
        this.calories = 0;
        this.recipe = "";
        this.micronutrients = new JSONObject();
        this.macronutrients = new JSONObject();
    }

    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }

    public int getCalories() {
        return calories;
    }

    public JSONObject getMicronutrients() {
        return micronutrients;
    }

    public JSONObject getMacronutrients() {
        return macronutrients;
    }

    public static List<Dish> listToDishes(List<String> dishes) {
        List<Dish> dishList = new ArrayList<>();
        for (String dish : dishes) {
            dishList.add(new Dish(dish));
        }
        return dishList;
    }
}