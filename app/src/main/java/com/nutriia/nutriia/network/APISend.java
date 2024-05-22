package com.nutriia.nutriia.network;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
                        String firstname = jsonObject.getString("firstname");
                        String lastname = jsonObject.getString("lastname");
                        String mail = jsonObject.getString("mail");
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

    public static void obtainsNewGoalAJR(Context context) {
        StringBuilder data = new StringBuilder("{");
        data.append("\"height\": ").append(UserSharedPreferences.getInstance(context).getHeight()).append(", ");
        data.append("\"weight\": ").append(UserSharedPreferences.getInstance(context).getWeight()).append(", ");
        data.append("\"goal_type\": ").append(UserSharedPreferences.getInstance(context).getGoal()).append(", ");
        data.append("\"progression\": ").append(UserSharedPreferences.getInstance(context).getProgression()).append(", ");
        data.append("\"activity_level\": ").append(UserSharedPreferences.getInstance(context).getActivityLevel()).append(", ");
        data.delete(data.length() - 2, data.length());
        data.append("}");

        Log.d("API", "Data: " + data);

        new APIRequest("get_new_goal", data.toString(), context).send(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body() != null ? response.body().string() : null;

                    Log.d("API", "Response: " + responseBody);

                } else Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
            }
        });
    }

}
