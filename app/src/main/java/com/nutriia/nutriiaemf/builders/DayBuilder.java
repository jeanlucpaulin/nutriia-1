package com.nutriia.nutriiaemf.builders;

import android.util.Log;

import com.nutriia.nutriiaemf.models.Day;
import com.nutriia.nutriiaemf.models.Nutrient;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * DayBuilder class
 * This class is used to build a Day from a JSON object or from UserSharedPreferences object
 */
public class DayBuilder {

    /**
     * Build a Day object from a JSON object
     * @param jsonObject JSON object
     * @param userSharedPreferences UserSharedPreferences object
     * @return
     */
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

    /**
     * Build a Day object from UserSharedPreferences object
     * @param sharedPreferences UserSharedPreferences object
     * @return
     */
    public Day buildOnlyWithGoal(UserSharedPreferences sharedPreferences) {
        Nutrient calorieNutrient = new Nutrient("calories", sharedPreferences.getRDACalories(), sharedPreferences.getNutrientUnit("calories"), sharedPreferences.getMRDCalories());
        Nutrient fiberNutrient = new Nutrient("fibers", sharedPreferences.getRDAFibers(), sharedPreferences.getNutrientUnit("fibers"), (int) sharedPreferences.getMRDFibers());

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        String date = sharedPreferences.getMRDDate();

        if(!currentDate.equals(date) && !date.isEmpty()) {
            Log.d("DayBuilder", "Clearing MRD");
            sharedPreferences.clearMRD();
            sharedPreferences.clearDayAnalysis();
        }

        Map<String, Nutrient> macroNutrients = new TreeMap<>();
        for(String key : sharedPreferences.getMacronutrients()) {
            macroNutrients.put(key, new Nutrient(key, (int) sharedPreferences.getRDANutrient(key), sharedPreferences.getNutrientUnit(key), (int) sharedPreferences.getMRDNutrient(key)));
        }

        Map<String, Nutrient> microNutrients = new TreeMap<>();
        for(String key : sharedPreferences.getMicronutrients()) {
            microNutrients.put(key, new Nutrient(key, (int) sharedPreferences.getRDANutrient(key), sharedPreferences.getNutrientUnit(key), (int) sharedPreferences.getMRDNutrient(key)));
        }

        String analysis = sharedPreferences.getDayAnalysis();

        return new Day(calorieNutrient, fiberNutrient, macroNutrients, microNutrients, analysis);
    }

}
