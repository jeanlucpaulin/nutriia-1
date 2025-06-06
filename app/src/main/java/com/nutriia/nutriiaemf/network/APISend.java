package com.nutriia.nutriiaemf.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.nutriia.nutriiaemf.models.Day;
import com.nutriia.nutriiaemf.models.Dish;
import com.nutriia.nutriiaemf.models.TypicalDay;
import com.nutriia.nutriiaemf.activities.DishRecipeActivity;
import com.nutriia.nutriiaemf.builders.DayBuilder;

import com.nutriia.nutriiaemf.interfaces.APIResponseRDA;
import com.nutriia.nutriiaemf.interfaces.OnValidateDay;
import com.nutriia.nutriiaemf.user.Saver;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * API send
 * Construct and send the API requests
 */
public class APISend {
    private static final String TAG = "APISend";
    private static boolean rdaDefined = false;
    private static boolean rdaRequested = false;
    private static final List<APIResponseRDA> listenersRDA = new ArrayList<>();

    public static void clearRDAListeners() {
        listenersRDA.clear();
    }

    public static void clear() {
        clearRDAListeners();
    }

    public static void addRDAListener(APIResponseRDA listener) {
        listenersRDA.add(listener);
        if(rdaDefined) listener.onAPIRDAResponse();
    }

    public static void sendConnect(String login, String password, Context context){
        String data = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        new APIRequest("connect", data, context).send( new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String token = jsonObject.getString("token");
                        UserSharedPreferences.getInstance(context).setAccessToken(token);
                        Log.d(TAG, "Token: " + token);
                    } catch (JSONException e) { e.printStackTrace(); }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainAccountInfos(Context context){
        new APIRequest("get_account_infos", "{}", context).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String firstname = jsonObject.getString("first_name");
                        String lastname = jsonObject.getString("last_name");
                        String mail = jsonObject.getString("email");
                        String login = jsonObject.getString("login");
                        Log.d(TAG, "Firstname: " + firstname + ", Lastname: " + lastname + ", Mail: " + mail + ", Login: " + login);
                    } catch (JSONException e) { e.printStackTrace(); }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsProfileInfos(Context context) {
        new APIRequest("get_profile_infos", "{}", context).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    Log.d(TAG, "Response: " + responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        int age = jsonObject.optInt("age");
                        int weight = jsonObject.optInt("weight");
                        int height = jsonObject.optInt("height");
                        int goal = jsonObject.optInt("goal");
                        int gender = jsonObject.optInt("gender");
                        int progression = jsonObject.optInt("progression");
                        int activityLevel = jsonObject.optInt("activity_level");

                        UserSharedPreferences.getInstance(context).setAge(age);
                        UserSharedPreferences.getInstance(context).setWeight(weight);
                        UserSharedPreferences.getInstance(context).setHeight(height);
                        UserSharedPreferences.getInstance(context).setGoal(goal);
                        UserSharedPreferences.getInstance(context).setGender(gender);
                        UserSharedPreferences.getInstance(context).setProgression(progression);
                        UserSharedPreferences.getInstance(context).setActivityLevel(activityLevel);
                        Log.d(TAG, "Age: " + age + ", Weight: " + weight + ", Height: " + height + ", Goal: " + goal + ", Gender: " + gender + ", Progression: " + progression + ", Activity Level: " + activityLevel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void sendProfileInfos(Context context, ArrayMap<String, Integer> profileInfos) {
        StringBuilder data = new StringBuilder("{");
        for (String key : profileInfos.keySet()) {
            data.append("\"").append(key).append("\": ").append(profileInfos.get(key)).append(", ");
        }
        data.delete(data.length() - 2, data.length());
        data.append("}");

        new APIRequest("set_profile_infos", data.toString(), context).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Profile infos updated");
                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }


    public static void obtainsNewGoalRDA(Activity activity, APIResponseRDA listener) {
        Log.d(TAG, "Obtains new goal RDA nb listeners: " + listenersRDA.size());
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(activity);

        rdaDefined = userSharedPreferences.isRDADefined();

        // If the user profile is already defined, we can directly call the listener
        if(rdaDefined) {
            Log.d(TAG, "User profile already defined");
            if(listener != null) addRDAListener(listener);
            activity.runOnUiThread(() -> listenersRDA.forEach(APIResponseRDA::onAPIRDAResponse));
            return;
        }

        if(listener != null) addRDAListener(listener);

        if(rdaRequested) return;

        rdaRequested = true;

        StringBuilder data = new StringBuilder("{\"user_profile\": {");
        data.append("\"height\": ").append(UserSharedPreferences.getInstance(activity).getHeight()).append(", ");
        data.append("\"weight\": ").append(UserSharedPreferences.getInstance(activity).getWeight()).append(", ");
        data.append("\"age\": ").append(UserSharedPreferences.getInstance(activity).getAge()).append(", ");
        data.append("\"gender\": ").append(UserSharedPreferences.getInstance(activity).getGender()).append(", ");
        data.append("\"goal_type\": ").append(UserSharedPreferences.getInstance(activity).getGoal()).append(", ");
        data.append("\"progression\": ").append(UserSharedPreferences.getInstance(activity).getProgression()).append(", ");
        data.append("\"activity_level\": ").append(UserSharedPreferences.getInstance(activity).getActivityLevel()).append(", ");
        data.delete(data.length() - 2, data.length());
        data.append("}}");

        Log.d(TAG, "Data: " + data);

        new APIRequest("get_new_goal", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                rdaRequested = false;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                rdaRequested = false;
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    Log.d(TAG, "Response: " + responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);

                        Set<String> macronutrientsList = new HashSet<>();
                        Set<String> micronutrientsList = new HashSet<>();

                        JSONObject macronutrientsJSON = jsonObject.getJSONObject("macronutrients");
                        for (Iterator<String> it = macronutrientsJSON.keys(); it.hasNext(); ) {
                            String key = it.next();
                            JSONObject nutrient = macronutrientsJSON.getJSONObject(key);
                            macronutrientsList.add(key);
                            userSharedPreferences.setRDANutrient(key, (float) nutrient.getDouble("value"));
                            userSharedPreferences.setNutrientUnit(key, nutrient.getString("unit"));
                        }

                        userSharedPreferences.setMacronutrients(macronutrientsList);

                        JSONObject micronutrientsJSON = jsonObject.getJSONObject("micronutrients");

                        for (Iterator<String> it = micronutrientsJSON.keys(); it.hasNext(); ) {
                            String key = it.next();
                            JSONObject nutrient = micronutrientsJSON.getJSONObject(key);
                            micronutrientsList.add(key);
                            userSharedPreferences.setRDANutrient(key, (float) nutrient.getDouble("value"));
                            userSharedPreferences.setNutrientUnit(key, nutrient.getString("unit"));
                        }

                        userSharedPreferences.setMicronutrients(micronutrientsList);

                        userSharedPreferences.setRDACalories(jsonObject.getJSONObject("calories").getInt("value"));
                        userSharedPreferences.setNutrientUnit("calories", jsonObject.getJSONObject("calories").getString("unit"));
                        userSharedPreferences.setRDAFibers(jsonObject.getJSONObject("fibers").getInt("value"));
                        userSharedPreferences.setNutrientUnit("fibers", jsonObject.getJSONObject("fibers").getString("unit"));

                        APISend.rdaDefined = true;

                        activity.runOnUiThread(() -> listenersRDA.forEach(APIResponseRDA::onAPIRDAResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsNewDish(Activity activity, Consumer<List<Dish>> callbackDishes, List<Dish> dishes) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", "get_dish_suggestion");

            JSONObject userGoal = new JSONObject();
            userGoal.put("calories", UserSharedPreferences.getInstance(activity).getRDACalories());
            for(String macronutrient : UserSharedPreferences.getInstance(activity).getMacronutrients()) {
                userGoal.put(macronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(macronutrient));
            }
            for(String micronutrient : UserSharedPreferences.getInstance(activity).getMicronutrients()) {
                userGoal.put(micronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(micronutrient));
            }
            data.put("user_goal", userGoal);

            JSONObject userRegistrations = new JSONObject();
            userRegistrations.put("breakfast",  UserSharedPreferences.getInstance(activity).getMRDABreakfast());
            userRegistrations.put("lunch", UserSharedPreferences.getInstance(activity).getMRDABreakfast());
            userRegistrations.put("snack", UserSharedPreferences.getInstance(activity).getMRDASnack());
            userRegistrations.put("dinner", UserSharedPreferences.getInstance(activity).getMRDADinner());
            data.put("user_registrations", userRegistrations);

            JSONObject userProfile = new JSONObject();
            userProfile.put("height", UserSharedPreferences.getInstance(activity).getHeight());
            userProfile.put("weight", UserSharedPreferences.getInstance(activity).getWeight());
            userProfile.put("goal_type", UserSharedPreferences.getInstance(activity).getGoal());
            userProfile.put("progression", UserSharedPreferences.getInstance(activity).getProgression());
            userProfile.put("activity_level", UserSharedPreferences.getInstance(activity).getActivityLevel());
            data.put("user_profile", userProfile);

            JSONObject userDay = new JSONObject();
            userDay.put("calories", UserSharedPreferences.getInstance(activity).getMRDCalories());
            for(String nutrient : UserSharedPreferences.getInstance(activity).getMacronutrients()) {
                userDay.put(nutrient, UserSharedPreferences.getInstance(activity).getMRDNutrient(nutrient));
            }
            for(String nutrient : UserSharedPreferences.getInstance(activity).getMicronutrients()) {
                userDay.put(nutrient, UserSharedPreferences.getInstance(activity).getMRDNutrient(nutrient));
            }
            data.put("user_day", userDay);


            JSONArray bannedDishes = new JSONArray();
            for(Dish dish : dishes) {
                bannedDishes.put(dish.getName());
            }

            data.put("banned_dishes", bannedDishes);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIRequest("get_dish_suggestion", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    if (responseBody != null && responseBody.startsWith("{")) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String dishName = jsonObject.getString("dish");
                            List<Dish> dishes = new ArrayList<>();
                            dishes.add(new Dish(dishName));

                            activity.runOnUiThread(() -> callbackDishes.accept(dishes));
                        } catch (JSONException e) {
                            Log.e(TAG, "Failed to parse JSON response", e);
                            // Handle the error appropriately
                        }
                    } else {
                        Log.e(TAG, "Response is not a valid JSON: " + responseBody);
                        // Handle the error appropriately
                    }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsTypicalDay(Activity activity, Consumer<List<TypicalDay>> callbackDishes, APIResponseRDA listener) {

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(activity);

        if(!rdaDefined && !userSharedPreferences.isTypicalDayDefined()) {
            if(!listenersRDA.contains(listener)) listenersRDA.add(listener);
            return;
        }

        if(userSharedPreferences.isTypicalDayDefined()) {

            List<TypicalDay> typicalDay = new ArrayList<>();
            typicalDay.add(new TypicalDay("breakfast", Dish.listToDishes(new ArrayList<>(userSharedPreferences.getTypicalDayBreakfast()))));
            typicalDay.add(new TypicalDay("lunch", Dish.listToDishes(new ArrayList<>(userSharedPreferences.getTypicalDayLunch()))));
            typicalDay.add(new TypicalDay("snack", Dish.listToDishes(new ArrayList<>(userSharedPreferences.getTypicalDaySnack()))));
            typicalDay.add(new TypicalDay("dinner", Dish.listToDishes(new ArrayList<>(userSharedPreferences.getTypicalDayDinner()))));

            activity.runOnUiThread(() -> callbackDishes.accept(typicalDay));
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("action", "get_typical_day");

            List<String> macronutrients = new ArrayList<>(UserSharedPreferences.getInstance(activity).getMacronutrients());
            List<String> micronutrients = new ArrayList<>(UserSharedPreferences.getInstance(activity).getMicronutrients());

            JSONObject userGoal = new JSONObject();
            userGoal.put("calories", UserSharedPreferences.getInstance(activity).getRDACalories());
            userGoal.put("fibers", UserSharedPreferences.getInstance(activity).getRDAFibers());
            JSONObject userMacronutrients = new JSONObject();
            JSONObject userMicronutrients = new JSONObject();

            for(String macronutrient : macronutrients) {
                userMacronutrients.put(macronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(macronutrient));
            }

            for(String micronutrient : micronutrients) {
                userMicronutrients.put(micronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(micronutrient));
            }

            Log.d(TAG, "size :" + (userGoal.length() + userMacronutrients.length() + userMicronutrients.length()));

            userGoal.put("macronutrients", userMacronutrients);
            userGoal.put("micronutrients", userMicronutrients);


            data.put("user_goal", userGoal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIRequest("get_typical_day", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.runOnUiThread(() -> callbackDishes.accept(new ArrayList<>()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                List<TypicalDay> typicalDay = new ArrayList<>();
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        Log.d(TAG, "Typical day send" + data.toString());
                        Log.d(TAG, "Typical day response: " + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        TypicalDay breakfast = new TypicalDay("breakfast", parseDishes(jsonObject.getJSONArray("breakfast")));
                        TypicalDay lunch = new TypicalDay("lunch", parseDishes(jsonObject.getJSONArray("lunch")));
                        TypicalDay snack = new TypicalDay("snack", parseDishes(jsonObject.getJSONArray("snack")));
                        TypicalDay dinner = new TypicalDay("dinner", parseDishes(jsonObject.getJSONArray("dinner")));


                        typicalDay.add(breakfast);
                        typicalDay.add(lunch);
                        typicalDay.add(snack);
                        typicalDay.add(dinner);

                        userSharedPreferences.setTypicalDayBreakfast(breakfast.getDishesStringSet());
                        userSharedPreferences.setTypicalDayLunch(lunch.getDishesStringSet());
                        userSharedPreferences.setTypicalDaySnack(snack.getDishesStringSet());
                        userSharedPreferences.setTypicalDayDinner(dinner.getDishesStringSet());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
                activity.runOnUiThread(() -> callbackDishes.accept(typicalDay));
            }
        });
    }

    private static List<Dish> parseDishes(JSONArray jsonArray) throws JSONException {
        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            dishes.add(new Dish(jsonArray.getString(i)));
        }
        return dishes;
    }

    public static void sendValidateDay(Activity activity, Map<String, Set<String>> userInput, OnValidateDay callback) {
        Context context = activity.getApplicationContext();
        JSONObject data = new JSONObject();
        try {
            data.put("action", "validate_day");
            data.put("user_registrations", new JSONObject(userInput));

            List<String> macronutrients = new ArrayList<>(UserSharedPreferences.getInstance(activity).getMacronutrients());
            List<String> micronutrients = new ArrayList<>(UserSharedPreferences.getInstance(activity).getMicronutrients());

            JSONObject userGoal = new JSONObject();
            userGoal.put("calories", UserSharedPreferences.getInstance(activity).getRDACalories());
            userGoal.put("fibers", UserSharedPreferences.getInstance(activity).getRDAFibers());
            JSONObject userMacronutrients = new JSONObject();
            JSONObject userMicronutrients = new JSONObject();

            for(String macronutrient : macronutrients) {
                userMacronutrients.put(macronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(macronutrient));
            }

            for(String micronutrient : micronutrients) {
                userMicronutrients.put(micronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(micronutrient));
            }

            Log.d(TAG, "size :" + (userGoal.length() + userMacronutrients.length() + userMicronutrients.length()));

            userGoal.put("macronutrients", userMacronutrients);
            userGoal.put("micronutrients", userMicronutrients);


            data.put("user_goal", userGoal);


            Log.d(TAG, "Data: " + data.toString());

            new APIRequest("validate_day", data.toString(), context).send(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    activity.runOnUiThread(() -> callback.onValidateDayResponse(null));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body() != null ? response.body().string() : null;
                        Log.d(TAG, "Day validated");
                        Log.d(TAG, "Response: " + responseBody);
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            Day day = new DayBuilder().build(jsonObject, UserSharedPreferences.getInstance(context));
                            Saver.saveMyReadDay(context, day);
                            activity.runOnUiThread(() -> callback.onValidateDayResponse(day));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
                        activity.runOnUiThread(() -> callback.onValidateDayResponse(null));
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void obtainsDishComposition(Activity activity, String dishName, Consumer<Dish> callbackDish) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", "get_dish_composition");
            data.put("dish_name", dishName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Show the loading spinner
        if (activity instanceof DishRecipeActivity) {
            activity.runOnUiThread(() -> ((DishRecipeActivity) activity).loadingSpinner.setVisibility(View.VISIBLE));
        }

        new APIRequest("get_dish_composition", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Hide the loading spinner
                if (activity instanceof DishRecipeActivity) {
                    activity.runOnUiThread(() -> ((DishRecipeActivity) activity).loadingSpinner.setVisibility(View.GONE));
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // Hide the loading spinner
                if (activity instanceof DishRecipeActivity) {
                    activity.runOnUiThread(() -> ((DishRecipeActivity) activity).loadingSpinner.setVisibility(View.GONE));
                }

                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        Dish dish = new Dish(jsonObject);

                        // Save the dish composition data
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("DishComposition", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(dishName, jsonObject.toString());
                        editor.apply();

                        activity.runOnUiThread(() -> callbackDish.accept(dish));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d(TAG, "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainDishRecipe(Activity activity, String dishName, Consumer<List<String>> callbackRecipe, Consumer<List<String>> callbackIngredients) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", "get_recipe");
            data.put("dish_name", dishName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIRequest("get_recipe", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject recipeObject = jsonObject.getJSONObject("recipe");
                        List<String> recipeList = new ArrayList<>();
                        Iterator<String> keys = recipeObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            recipeList.add(recipeObject.getString(key));
                        }

                        JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
                        List<String> ingredientsList = new ArrayList<>();
                        for (int i = 0; i < ingredientsArray.length(); i++) {
                            ingredientsList.add(ingredientsArray.getString(i));
                        }

                        activity.runOnUiThread(() -> {
                            callbackRecipe.accept(recipeList);
                            callbackIngredients.accept(ingredientsList);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
