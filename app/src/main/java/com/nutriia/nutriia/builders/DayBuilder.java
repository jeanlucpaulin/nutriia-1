package com.nutriia.nutriia.builders;

import android.util.Log;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DayBuilder {

    public Day build(JSONObject jsonObject, UserSharedPreferences userSharedPreferences) {

        try {
            JSONObject calorie = jsonObject.getJSONObject("calories");
            Nutrient calorieNutrient = new Nutrient("calories", userSharedPreferences.getRDACalories(), calorie.getString("unit"), calorie.getInt("value"));

            Map<String, Nutrient> macroNutrients = new HashMap<>();
            JSONObject macroNutrientsJson = jsonObject.getJSONObject("macronutrients");
            for(Iterator<String> it = macroNutrientsJson.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject nutrient = macroNutrientsJson.getJSONObject(key);

                macroNutrients.put(key, new Nutrient(key, (int) userSharedPreferences.getRDANutrient(key), nutrient.getString("unit"), nutrient.getInt("value")));
            }

            Map<String, Nutrient> microNutrients = new HashMap<>();
            JSONObject microNutrientsJson = jsonObject.getJSONObject("micronutrients");
            for(Iterator<String> it = microNutrientsJson.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject nutrient = microNutrientsJson.getJSONObject(key);
                microNutrients.put(key, new Nutrient(key, (int) userSharedPreferences.getRDANutrient(key), nutrient.getString("unit"), nutrient.getInt("value")));
            }

            return new Day(calorieNutrient, macroNutrients, microNutrients);

        } catch (JSONException e) {
            Log.e("API DayBuilder", "Error while building day from JSON", e);

            return null;
        }
    }

}
