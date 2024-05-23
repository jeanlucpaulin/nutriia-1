package com.nutriia.nutriia.network;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.fragments.NutrientAJR;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class APISend {

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
                macronutrientsFragments.add(new NutrientAJR(new Nutrient(macronutrient, (int) userSharedPreferences.getRDANutrient(macronutrient), "g")));
            }

            for (String micronutrient : micronutrients) {
                micronutrientsFragments.add(new NutrientAJR(new Nutrient(micronutrient, (int) userSharedPreferences.getRDANutrient(micronutrient), "mg")));
            }

            activity.runOnUiThread(() -> callbackMacro.accept(macronutrientsFragments));
            activity.runOnUiThread(() -> callbackMicro.accept(micronutrientsFragments));
            activity.runOnUiThread(() -> callbackCalories.accept(String.valueOf(userSharedPreferences.getRDACalories())));

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
                            macronutrients.add(new NutrientAJR(new Nutrient(key, nutrient.getInt("value"), nutrient.getString("unit"))));
                        }

                        userSharedPreferences.setMacronutrients(macronutrientsList);

                        JSONObject micronutrientsJSON = jsonObject.getJSONObject("micronutrients");
                        for (Iterator<String> it = micronutrientsJSON.keys(); it.hasNext(); ) {
                            String key = it.next();
                            JSONObject nutrient = micronutrientsJSON.getJSONObject(key);
                            micronutrientsList.add(key);
                            userSharedPreferences.setRDANutrient(key, nutrient.getInt("value"));
                            micronutrients.add(new NutrientAJR(new Nutrient(key, nutrient.getInt("value"), nutrient.getString("unit"))));
                        }

                        userSharedPreferences.setMicronutrients(micronutrientsList);

                        String calories = jsonObject.getJSONObject("calories").getString("value");

                        userSharedPreferences.setRDACalories(jsonObject.getJSONObject("calories").getInt("value"));

                        activity.runOnUiThread(() -> callbackMacro.accept(macronutrients));
                        activity.runOnUiThread(() -> callbackMicro.accept(micronutrients));
                        activity.runOnUiThread(() -> callbackCalories.accept(calories));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

}
