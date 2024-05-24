package com.nutriia.nutriia.network;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.TypicalDay;
import com.nutriia.nutriia.fragments.NutrientAJR;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.resources.Translator;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class APISend {

    private static boolean rdaDefined = false;
    private static final List<APIResponseRDA> listeners = new ArrayList<>();

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
                        Log.d("API", "Token: " + token);
                    } catch (JSONException e) { e.printStackTrace(); }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
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
                        Log.d("API", "Firstname: " + firstname + ", Lastname: " + lastname + ", Mail: " + mail + ", Login: " + login);
                    } catch (JSONException e) { e.printStackTrace(); }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
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

                    Log.d("API", "Response: " + responseBody);

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
                        Log.d("API", "Age: " + age + ", Weight: " + weight + ", Height: " + height + ", Goal: " + goal + ", Gender: " + gender + ", Progression: " + progression + ", Activity Level: " + activityLevel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
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
                    Log.d("API", "Profile infos updated");
                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsNewGoalAJR(Activity activity, Consumer<ArrayList<Fragment>> callbackMacro, Consumer<ArrayList<Fragment>> callbackMicro, Consumer<String> callbackCalories) {

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(activity);
        if(userSharedPreferences.isRDADefined())
        {
            Log.d("API", "User profile already defined");
            ArrayList<String> macronutrients = new ArrayList<>(userSharedPreferences.getMacronutrients());
            ArrayList<String> micronutrients = new ArrayList<>(userSharedPreferences.getMicronutrients());

            ArrayList<Fragment> macronutrientsFragments = new ArrayList<>();
            ArrayList<Fragment> micronutrientsFragments = new ArrayList<>();

            for (String macronutrient : macronutrients) {
                macronutrientsFragments.add(new NutrientAJR(new Nutrient(Translator.translate(macronutrient), (int) userSharedPreferences.getRDANutrient(macronutrient), "g")));
            }

            for (String micronutrient : micronutrients) {
                micronutrientsFragments.add(new NutrientAJR(new Nutrient(Translator.translate(micronutrient), (int) userSharedPreferences.getRDANutrient(micronutrient), "mg")));
            }

            activity.runOnUiThread(() -> callbackMacro.accept(macronutrientsFragments));
            activity.runOnUiThread(() -> callbackMicro.accept(micronutrientsFragments));
            activity.runOnUiThread(() -> callbackCalories.accept(String.valueOf(userSharedPreferences.getRDACalories())));
            APISend.rdaDefined = true;
            activity.runOnUiThread(() -> listeners.forEach(APIResponseRDA::onAPIRDAResponse));
            return;
        }

        StringBuilder data = new StringBuilder("{\"user_profile\": {");
        data.append("\"height\": ").append(UserSharedPreferences.getInstance(activity).getHeight()).append(", ");
        data.append("\"weight\": ").append(UserSharedPreferences.getInstance(activity).getWeight()).append(", ");
        data.append("\"goal_type\": ").append(UserSharedPreferences.getInstance(activity).getGoal()).append(", ");
        data.append("\"progression\": ").append(UserSharedPreferences.getInstance(activity).getProgression()).append(", ");
        data.append("\"activity_level\": ").append(UserSharedPreferences.getInstance(activity).getActivityLevel()).append(", ");
        data.delete(data.length() - 2, data.length());
        data.append("}}");

        Log.d("API", "Data: " + data);

        new APIRequest("get_new_goal", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    Log.d("API", "Response: " + responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        ArrayList<Fragment> macronutrients = new ArrayList<>();
                        ArrayList<Fragment> micronutrients = new ArrayList<>();
                        Set<String> macronutrientsList = new HashSet<>();
                        Set<String> micronutrientsList = new HashSet<>();

                        JSONObject macronutrientsJSON = jsonObject.getJSONObject("macronutrients");
                        for (Iterator<String> it = macronutrientsJSON.keys(); it.hasNext(); ) {
                            String key = it.next();
                            JSONObject nutrient = macronutrientsJSON.getJSONObject(key);
                            macronutrientsList.add(key);
                            userSharedPreferences.setRDANutrient(key, nutrient.getInt("value"));
                            macronutrients.add(new NutrientAJR(new Nutrient(Translator.translate(key), nutrient.getInt("value"), nutrient.getString("unit"))));
                        }

                        userSharedPreferences.setMacronutrients(macronutrientsList);

                        JSONObject micronutrientsJSON = jsonObject.getJSONObject("micronutrients");
                        for (Iterator<String> it = micronutrientsJSON.keys(); it.hasNext(); ) {
                            String key = it.next();
                            JSONObject nutrient = micronutrientsJSON.getJSONObject(key);
                            micronutrientsList.add(key);
                            userSharedPreferences.setRDANutrient(key, nutrient.getInt("value"));
                            micronutrients.add(new NutrientAJR(new Nutrient(Translator.translate(key), nutrient.getInt("value"), nutrient.getString("unit"))));
                        }

                        userSharedPreferences.setMicronutrients(micronutrientsList);

                        String calories = jsonObject.getJSONObject("calories").getString("value");

                        userSharedPreferences.setRDACalories(jsonObject.getJSONObject("calories").getInt("value"));

                        activity.runOnUiThread(() -> callbackMacro.accept(macronutrients));
                        activity.runOnUiThread(() -> callbackMicro.accept(micronutrients));
                        activity.runOnUiThread(() -> callbackCalories.accept(calories));
                        APISend.rdaDefined = true;
                        activity.runOnUiThread(() -> listeners.forEach(APIResponseRDA::onAPIRDAResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsNewDish(Activity activity, Consumer<List<Dish>> callbackDishes) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", "get_dish_suggestion");

            JSONObject userGoal = new JSONObject();
            userGoal.put("calories", new JSONArray());
            userGoal.put("proteins", new JSONArray());
            data.put("user_goal", userGoal);

            JSONObject userRegistrations = new JSONObject();
            userRegistrations.put("breakfast",  new JSONArray());
            userRegistrations.put("lunch", new JSONArray());
            userRegistrations.put("snack", new JSONArray());
            userRegistrations.put("dinner", new JSONArray());
            data.put("user_registrations", userRegistrations);

            JSONObject userProfile = new JSONObject();
            userProfile.put("height", UserSharedPreferences.getInstance(activity).getHeight());
            userProfile.put("weight", UserSharedPreferences.getInstance(activity).getWeight());
            userProfile.put("goal_type", UserSharedPreferences.getInstance(activity).getGoal());
            userProfile.put("progression", UserSharedPreferences.getInstance(activity).getProgression());
            userProfile.put("activity_level", UserSharedPreferences.getInstance(activity).getActivityLevel());
            data.put("user_profile", userProfile);

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

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String dishName = jsonObject.getString("dish");
                        List<Dish> dishes = new ArrayList<>();
                        dishes.add(new Dish(dishName));

                        activity.runOnUiThread(() -> callbackDishes.accept(dishes));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

    public static void obtainsTypicalDay(Activity activity, Consumer<List<TypicalDay>> callbackDishes, APIResponseRDA listener) {

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(activity);

        if(!rdaDefined && !userSharedPreferences.isTypicalDayDefined()) {
            if(!listeners.contains(listener)) listeners.add(listener);
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
            JSONObject userMacronutrients = new JSONObject();
            JSONObject userMicronutrients = new JSONObject();

            for(String macronutrient : macronutrients) {
                userMacronutrients.put(macronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(macronutrient));
            }

            for(String micronutrient : micronutrients) {
                userMicronutrients.put(micronutrient, UserSharedPreferences.getInstance(activity).getRDANutrient(micronutrient));
            }

            userGoal.put("macronutrients", userMacronutrients);
            userGoal.put("micronutrients", userMicronutrients);

            data.put("user_goal", userGoal);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIRequest("get_typical_day", data.toString(), activity).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        TypicalDay breakfast = new TypicalDay("breakfast", parseDishes(jsonObject.getJSONArray("breakfast")));
                        TypicalDay lunch = new TypicalDay("lunch", parseDishes(jsonObject.getJSONArray("lunch")));
                        TypicalDay snack = new TypicalDay("snack", parseDishes(jsonObject.getJSONArray("snack")));
                        TypicalDay dinner = new TypicalDay("dinner", parseDishes(jsonObject.getJSONArray("dinner")));

                        List<TypicalDay> typicalDay = new ArrayList<>();
                        typicalDay.add(breakfast);
                        typicalDay.add(lunch);
                        typicalDay.add(snack);
                        typicalDay.add(dinner);

                        userSharedPreferences.setTypicalDayBreakfast(breakfast.getDishesStringSet());
                        userSharedPreferences.setTypicalDayLunch(lunch.getDishesStringSet());
                        userSharedPreferences.setTypicalDaySnack(snack.getDishesStringSet());
                        userSharedPreferences.setTypicalDayDinner(dinner.getDishesStringSet());


                        activity.runOnUiThread(() -> callbackDishes.accept(typicalDay));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
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

    public static void sendValidateDay(Context context){

    }
}
