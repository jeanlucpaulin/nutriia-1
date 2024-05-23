package com.nutriia.nutriia.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class UserSharedPreferences {
    private static final String USER_PREFERENCES = "user_preferences";
    private static final String KEY_GOAL = "user_goal";
    private static final String KEY_HEIGHT = "user_height";
    private static final String KEY_WEIGHT = "user_weight";
    private static final String KEY_AGE = "user_age";
    private static final String KEY_GENDER ="user_gender";
    private static final String KEY_ACTIVITY_LEVEL = "user_activity_level";
    private static final String KEY_PROGRESSION = "user_progression";

    private static final String KEY_RDA = "RDA_";
    private static final String KEY_CALORIES = "user_calories";
    private static final String KEY_MACRONUTRIENTS = "user_macronutrients";
    private static final String KEY_MICRONUTRIENTS = "user_micronutrients";

    private static final String KEY_RDA_CALORIES = "user_rda_calories";
    private static final String KEY_RDA_PROTEINS = "user_rda_proteins";
    private static final String KEY_RDA_LIPIDS = "user_rda_lipids";
    private static final String KEY_RDA_CARBOHYDRATES = "user_rda_carbohydrates";
    private static final String KEY_RDA_FIBERS = "user_rda_fibers";
    private static final String KEY_RDA_SUGARS = "user_rda_sugars";
    private static final String KEY_RDA_VITAMIN_A = "user_rda_vitamin_a";
    private static final String KEY_RDA_VITAMIN_B1 = "user_rda_vitamin_b1";
    private static final String KEY_RDA_VITAMIN_B2 = "user_rda_vitamin_b2";
    private static final String KEY_RDA_VITAMIN_B3 = "user_rda_vitamin_b3";
    private static final String KEY_RDA_VITAMIN_B4 = "user_rda_vitamin_b4";
    private static final String KEY_RDA_VITAMIN_B6 = "user_rda_vitamin_b6";
    private static final String KEY_RDA_VITAMIN_B7 = "user_rda_vitamin_b7";
    private static final String KEY_RDA_VITAMIN_B9 = "user_rda_vitamin_b9";
    private static final String KEY_RDA_VITAMIN_C = "user_rda_vitamin_c";
    private static final String KEY_RDA_VITAMIN_D = "user_rda_vitamin_d";
    private static final String KEY_RDA_VITAMIN_E = "user_rda_vitamin_e";
    private static final String KEY_RDA_VITAMIN_K = "user_rda_vitamin_k";
    private static final String KEY_RDA_CALCIUM = "user_rda_calcium";
    private static final String KEY_RDA_COPPER = "user_rda_copper";
    private static final String KEY_RDA_IRON = "user_rda_iron";
    private static final String KEY_RDA_MAGNESIUM = "user_rda_magnesium";
    private static final String KEY_RDA_MANGANESE = "user_rda_manganese";
    private static final String KEY_RDA_PHOSPHORUS = "user_rda_phosphorus";
    private static final String KEY_RDA_POTASSIUM = "user_rda_potassium";
    private static final String KEY_RDA_ZINC = "user_rda_zinc";





    private final SharedPreferences sharedPreferences;
    private static UserSharedPreferences instance;

    private UserSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static UserSharedPreferences getInstance(Context context) {
        if (instance == null) instance = new UserSharedPreferences(context);
        return instance;
    }

    public boolean isUserProfileDefined() {
        return isUserHeightDefined() && isUserWeightDefined();
    }

    public void setGoal(int goal) {
        sharedPreferences.edit().putInt(KEY_GOAL, goal).apply();
    }

    public int getGoal() {
        return sharedPreferences.getInt(KEY_GOAL, -1);
    }

    public boolean isUserGoalDefined() {
        return sharedPreferences.getInt(KEY_GOAL, -1) > 0;
    }

    public void setHeight(int height) { sharedPreferences.edit().putInt(KEY_HEIGHT, height).apply(); }

    public int getHeight() { return sharedPreferences.getInt(KEY_HEIGHT, -1); }

    public boolean isUserHeightDefined() { return sharedPreferences.getInt(KEY_HEIGHT, -1) != -1; }

    public void setWeight(int weight) { sharedPreferences.edit().putInt(KEY_WEIGHT, weight).apply(); }

    public int getWeight() { return sharedPreferences.getInt(KEY_WEIGHT, -1); }

    public boolean isUserWeightDefined() { return sharedPreferences.getInt(KEY_WEIGHT, -1) != -1; }

    public void setAge(int age) { sharedPreferences.edit().putInt(KEY_AGE, age).apply(); }

    public int getAge() { return sharedPreferences.getInt(KEY_AGE, -1); }

    public boolean isUserAgeDefined() { return sharedPreferences.getInt(KEY_AGE, -1) != -1; }

    public void setGender(int gender) { sharedPreferences.edit().putInt(KEY_GENDER, gender).apply(); }

    public int getGender() { return sharedPreferences.getInt(KEY_GENDER, -1); }

    public void setActivityLevel(int activityLevel) { sharedPreferences.edit().putInt(KEY_ACTIVITY_LEVEL, activityLevel).apply(); }

    public int getActivityLevel() { return sharedPreferences.getInt(KEY_ACTIVITY_LEVEL, -1); }

    public void setProgression(int progression) {
        sharedPreferences.edit().putInt(KEY_PROGRESSION, progression).apply();
    }

    public int getProgression() { return sharedPreferences.getInt(KEY_PROGRESSION, -1); }

    public void setRDACalories(int calories) { sharedPreferences.edit().putInt(KEY_CALORIES, calories).apply(); }

    public int getRDACalories() { return sharedPreferences.getInt(KEY_CALORIES, -1); }

    public void setRDANutrient(String nutrient, int value) { sharedPreferences.edit().putFloat(KEY_RDA + nutrient, value).apply(); }

    public float getRDANutrient(String nutrient) { return sharedPreferences.getFloat(KEY_RDA + nutrient, -1); }

    public Set<String> getMacronutrients() {
        return sharedPreferences.getStringSet(KEY_MACRONUTRIENTS, new HashSet<>());
    }

    public void setMacronutrients(Set<String> macronutrients) {
        sharedPreferences.edit().putStringSet(KEY_MACRONUTRIENTS, macronutrients).apply();
    }

    public Set<String> getMicronutrients() {
        return sharedPreferences.getStringSet(KEY_MICRONUTRIENTS, new HashSet<>());
    }

    public void setMicronutrients(Set<String> micronutrients) {
        sharedPreferences.edit().putStringSet(KEY_MICRONUTRIENTS, micronutrients).apply();
    }

    public boolean isRDADefined() {
        return getRDACalories() != -1;
    }

    public void clearRDA()
    {
        Log.d("UserSharedPreferences", "Clearing RDA");
        for(String nutrient : getMacronutrients())
        {
            sharedPreferences.edit().remove(KEY_RDA + nutrient).apply();
        }

        for(String nutrient : getMicronutrients())
        {
            sharedPreferences.edit().remove(KEY_RDA + nutrient).apply();
        }

        sharedPreferences.edit().remove(KEY_CALORIES).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString("access_token", "");
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString("access_token", accessToken).apply();
    }
}
