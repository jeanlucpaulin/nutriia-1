package com.nutriia.nutriia.network;
import android.content.Context;
import android.util.Log;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.user.UserSharedPreferences;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class APIRequest {

    private static final OkHttpClient client = new OkHttpClient();

    private String action;
    private String data;
    private Context context;

    public APIRequest(String action, String data, Context context) {
        this.action = action;
        this.data = data;
        this.context = context;
    }

    public void send(Callback callback) {
        try {
            String encodedData = data == null ? "" : data;

            String data = "action=" + this.action + "&request=" + encodedData;

            if(context == null) {
                Log.e("APIRequest", "Context is null");
                return;
            }

            String url = this.context.getString(R.string.api_client_url) + "?" + data;

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + UserSharedPreferences.getInstance(this.context).getAccessToken())
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            Log.e("APIRequest", "Error encoding data", e);
        }
    }
}
