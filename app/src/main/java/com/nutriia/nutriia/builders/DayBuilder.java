package com.nutriia.nutriia.builders;

import android.util.Log;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DayBuilder {

    public Day build(JSONObject jsonObject, UserSharedPreferences userSharedPreferences) {

        try {
            JSONObject calorie = jsonObject.getJSONObject("calories");
            Nutrient calorieNutrient = new Nutrient("calories", userSharedPreferences.getRDACalories(), calorie.getString("unit"), calorie.getInt("value"));

            JSONObject fiber = jsonObject.getJSONObject("fibers");
            Nutrient fiberNutrient = new Nutrient("fibers", userSharedPreferences.getRDAFibers(), fiber.getString("unit"), fiber.getInt("value"));

            Map<String, Nutrient> macroNutrients = new TreeMap<>();
            JSONObject macroNutrientsJson = jsonObject.getJSONObject("macronutrients");
            for(Iterator<String> it = macroNutrientsJson.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject nutrient = macroNutrientsJson.getJSONObject(key);

                macroNutrients.put(key, new Nutrient(key, (int) userSharedPreferences.getRDANutrient(key), nutrient.getString("unit"), nutrient.getInt("value")));
            }

            Map<String, Nutrient> microNutrients = new TreeMap<>();
            JSONObject microNutrientsJson = jsonObject.getJSONObject("micronutrients");
            for(Iterator<String> it = microNutrientsJson.keys(); it.hasNext(); ) {
                String key = it.next();
                JSONObject nutrient = microNutrientsJson.getJSONObject(key);
                microNutrients.put(key, new Nutrient(key, (int) userSharedPreferences.getRDANutrient(key), nutrient.getString("unit"), nutrient.getInt("value")));
            }

            String analysis = jsonObject.getString("analysis");

            userSharedPreferences.setDayAnalysis(analysis);

            return new Day(calorieNutrient, fiberNutrient, macroNutrients, microNutrients, analysis);

        } catch (JSONException e) {
            Log.e("API DayBuilder", "Error while building day from JSON", e);

            return null;
        }
    }

    public Day buildOnlyWithGoal(UserSharedPreferences sharedPreferences) {
        Nutrient calorieNutrient = new Nutrient("calories", sharedPreferences.getRDACalories(), "kcal", 0);
        Nutrient fiberNutrient = new Nutrient("fibers", sharedPreferences.getRDAFibers(), "g", 0);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        String date = sharedPreferences.getMRDDate();

        Log.d("DayBuilder", "Current date: " + currentDate);
        Log.d("DayBuilder", "Date: " + date);

        if(!currentDate.equals(date) && !date.isEmpty()) {
            Log.d("DayBuilder", "Clearing MRD");
            sharedPreferences.clearMRD();
        }

        Map<String, Nutrient> macroNutrients = new TreeMap<>();
        for(String key : sharedPreferences.getMacronutrients()) {
            macroNutrients.put(key, new Nutrient(key, (int) sharedPreferences.getRDANutrient(key), "g", (int) sharedPreferences.getMRDNutrient(key)));
        }

        Map<String, Nutrient> microNutrients = new TreeMap<>();
        for(String key : sharedPreferences.getMicronutrients()) {
            microNutrients.put(key, new Nutrient(key, (int) sharedPreferences.getRDANutrient(key), "mg", (int) sharedPreferences.getMRDNutrient(key)));
        }

        String analysis = sharedPreferences.getDayAnalysis();

        return new Day(calorieNutrient, fiberNutrient, macroNutrients, microNutrients, analysis);
    }

}
