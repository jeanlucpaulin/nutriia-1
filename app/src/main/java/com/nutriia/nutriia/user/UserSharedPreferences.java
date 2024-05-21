package com.nutriia.nutriia.user;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {
    private static final String USER_PREFERENCES = "user_preferences";
    private static final String KEY_GOAL = "user_goal";
    private static final String KEY_HEIGHT = "user_height";
    private static final String KEY_WEIGHT = "user_weight";
    private static final String KEY_AGE = "user_age";
    private static final String KEY_GENDER ="user_gender";
    private static final String KEY_ACTIVITY_LEVEL = "user_activity_level";
    private static final String KEY_PROGRESSION = "user_progression";

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

    public void setProgression(int progression) { sharedPreferences.edit().putInt(KEY_PROGRESSION, progression).apply(); }

    public int getProgression() { return sharedPreferences.getInt(KEY_PROGRESSION, -1); }


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
