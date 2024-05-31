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
    private static final String KEY_MY_DAY = "user_my_day_";
    private static final String KEY_MY_DAY_DATE = "user_my_day_date";
    private static final String KEY_MY_DAY_BREAKFAST = "user_my_day_breakfast";
    private static final String KEY_MY_DAY_LUNCH = "user_my_day_lunch";
    private static final String KEY_MY_DAY_DINNER = "user_my_day_dinner";
    private static final String KEY_MY_DAY_SNACK = "user_my_day_snack";
    private static final String KEY_MY_DAY_CALORIES = "user_my_day_calories";

    private static final String KEY_TYPICAL_DAY_BREAKFAST = "user_typical_day_breakfast";
    private static final String KEY_TYPICAL_DAY_LUNCH = "user_typical_day_lunch";
    private static final String KEY_TYPICAL_DAY_DINNER = "user_typical_day_dinner";
    private static final String KEY_TYPICAL_DAY_SNACK = "user_typical_day_snack";

    private static final String KEY_DISH_SUGGESTIONS = "user_dish_suggestions";
    private static final String KEY_DISH_SUGGESTIONS_DATE = "user_dish_suggestions_date";

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

    public boolean isUserGenderDefined() { return sharedPreferences.getInt(KEY_GENDER, -1) != -1; }

    public void setActivityLevel(int activityLevel) { sharedPreferences.edit().putInt(KEY_ACTIVITY_LEVEL, activityLevel).apply(); }

    public int getActivityLevel() { return sharedPreferences.getInt(KEY_ACTIVITY_LEVEL, -1); }

    public void setProgression(int progression) {
        sharedPreferences.edit().putInt(KEY_PROGRESSION, progression).apply();
    }

    public int getProgression() { return sharedPreferences.getInt(KEY_PROGRESSION, -1); }

    public void setRDACalories(int calories) { sharedPreferences.edit().putInt(KEY_CALORIES, calories).apply(); }

    public int getRDACalories() { return sharedPreferences.getInt(KEY_CALORIES, -1); }

    public void setRDANutrient(String nutrient, int value) { sharedPreferences.edit().putFloat(KEY_RDA + nutrient, value).apply(); }

    public float getRDANutrient(String nutrient) { return sharedPreferences.getFloat(KEY_RDA + nutrient, 0); }

    public void setMRDCalories(int calories) { sharedPreferences.edit().putInt(KEY_MY_DAY_CALORIES, calories).apply(); }

    public int getMRDCalories() { return sharedPreferences.getInt(KEY_MY_DAY_CALORIES, -1); }

    public void setMRDNutrient(String nutrient, int value) { sharedPreferences.edit().putFloat(KEY_MY_DAY + nutrient, value).apply(); }

    public float getMRDNutrient(String nutrient) { return sharedPreferences.getFloat(KEY_MY_DAY + nutrient, 0); }

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

    public boolean isMRDDefined() { return getMRDCalories() != -1; }

    public void setMRDDate(String date) {
        sharedPreferences.edit().putString(KEY_MY_DAY_DATE, date).apply();
    }

    public String getMRDDate() {
        return sharedPreferences.getString(KEY_MY_DAY_DATE, "");
    }

    /** Set MyRealDay Breakfast */
    public void setMRDABreakfast(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_MY_DAY_BREAKFAST, dish).apply();
    }

    /** Get MyRealDay Breakfast */
    public Set<String> getMRDABreakfast(){
        return sharedPreferences.getStringSet(KEY_MY_DAY_BREAKFAST, new HashSet<>());
    }

    /** Set MyRealDay Lunch */
    public void setMRDALunch(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_MY_DAY_LUNCH, dish).apply();
    }

    /** Get MyRealDay Lunch */
    public Set<String> getMRDALunch(){
        return sharedPreferences.getStringSet(KEY_MY_DAY_LUNCH, new HashSet<>());
    }

    /** Set MyRealDay Dinner */
    public void setMRDADinner(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_MY_DAY_DINNER, dish).apply();
    }

    /** Get MyRealDay Dinner */
    public Set<String> getMRDADinner(){
        return sharedPreferences.getStringSet(KEY_MY_DAY_DINNER, new HashSet<>());
    }

    /** Set MyRealDay Snack */
    public void setMRDASnack(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_MY_DAY_SNACK, dish).apply();
    }

    /** Get MyRealDay Snack */
    public Set<String> getMRDASnack(){
        return sharedPreferences.getStringSet(KEY_MY_DAY_SNACK, new HashSet<>());
    }

    public void clearMRD() {
        for(String nutrient : getMacronutrients())
        {
            sharedPreferences.edit().remove(KEY_MY_DAY + nutrient).apply();
        }
        for(String nutrient : getMicronutrients())
        {
            sharedPreferences.edit().remove(KEY_MY_DAY + nutrient).apply();
        }
        sharedPreferences.edit().remove(KEY_MY_DAY_CALORIES).apply();
        sharedPreferences.edit().remove(KEY_MY_DAY_DATE).apply();
        sharedPreferences.edit().remove(KEY_MY_DAY_BREAKFAST).apply();
        sharedPreferences.edit().remove(KEY_MY_DAY_LUNCH).apply();
        sharedPreferences.edit().remove(KEY_MY_DAY_DINNER).apply();
        sharedPreferences.edit().remove(KEY_MY_DAY_SNACK).apply();
    }

    public void clearRDA() {
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

    public void setTypicalDayBreakfast(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_TYPICAL_DAY_BREAKFAST, dish).apply();
    }

    public void setTypicalDayLunch(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_TYPICAL_DAY_LUNCH, dish).apply();
    }

    public void setTypicalDayDinner(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_TYPICAL_DAY_DINNER, dish).apply();
    }

    public void setTypicalDaySnack(Set<String> dish){
        sharedPreferences.edit().putStringSet(KEY_TYPICAL_DAY_SNACK, dish).apply();
    }

    public Set<String> getTypicalDayBreakfast(){
        return sharedPreferences.getStringSet(KEY_TYPICAL_DAY_BREAKFAST, new HashSet<>());
    }

    public Set<String> getTypicalDayLunch(){
        return sharedPreferences.getStringSet(KEY_TYPICAL_DAY_LUNCH, new HashSet<>());
    }

    public Set<String> getTypicalDayDinner(){
        return sharedPreferences.getStringSet(KEY_TYPICAL_DAY_DINNER, new HashSet<>());
    }

    public Set<String> getTypicalDaySnack(){
        return sharedPreferences.getStringSet(KEY_TYPICAL_DAY_SNACK, new HashSet<>());
    }

    public boolean isTypicalDayDefined(){
        return !getTypicalDayBreakfast().isEmpty() && !getTypicalDayLunch().isEmpty() && !getTypicalDayDinner().isEmpty() && !getTypicalDaySnack().isEmpty();
    }

    public void clearTypicalDay(){
        sharedPreferences.edit().remove(KEY_TYPICAL_DAY_BREAKFAST).apply();
        sharedPreferences.edit().remove(KEY_TYPICAL_DAY_LUNCH).apply();
        sharedPreferences.edit().remove(KEY_TYPICAL_DAY_DINNER).apply();
        sharedPreferences.edit().remove(KEY_TYPICAL_DAY_SNACK).apply();
    }

    public void setDishSuggestions(Set<String> dishSuggestions){
        sharedPreferences.edit().putStringSet(KEY_DISH_SUGGESTIONS, dishSuggestions).apply();
    }

    public Set<String> getDishSuggestions(){
        return sharedPreferences.getStringSet(KEY_DISH_SUGGESTIONS, new HashSet<>());
    }

    public void setDishSuggestionsDate(String date){
        sharedPreferences.edit().putString(KEY_DISH_SUGGESTIONS_DATE, date).apply();
    }

    public String getDishSuggestionsDate(){
        return sharedPreferences.getString(KEY_DISH_SUGGESTIONS_DATE, "");
    }
}
