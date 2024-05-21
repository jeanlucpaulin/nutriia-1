package com.nutriia.nutriia.network;
import android.content.Context;
import android.util.Log;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONObject;

import okhttp3.*;

public class APIRequest {

    private static final OkHttpClient client = new OkHttpClient();

    private String action;
    private JSONObject data;
    private Context context;

    public APIRequest(String action, JSONObject data, Context context) {
        this.action = action;
        this.data = data;
        this.context = context;
    }

    public void send(Callback callback) {
        try {
            String encodedData = data == null ? "" : data.toString();

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("action", this.action)
                    .add("request", encodedData);

            RequestBody requestBody = formBuilder.build();

            String url = this.context.getString(R.string.api_client_url);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.getInstance(this.context).getAccessToken())
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            Log.e("APIRequest", "Error encoding data", e);
        }
    }
}
