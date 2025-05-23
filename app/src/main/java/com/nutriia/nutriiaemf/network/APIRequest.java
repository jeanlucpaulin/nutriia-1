package com.nutriia.nutriiaemf.network;
import android.content.Context;
import android.util.Log;

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import okhttp3.*;

/**
 * API request
 */
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

    /**
     * Send the request to the API
     * @param callback
     */
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
