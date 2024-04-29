/* package com.example.nutriia1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ChatActivity extends AppCompatActivity {

    private EditText inputText;
    private Button sendButton;
    private TextView responseText;

    private ProgressBar progressBar;

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputText = findViewById(R.id.inputText);
        sendButton = findViewById(R.id.sendButton);
        responseText = findViewById(R.id.responseText);

        progressBar = findViewById(R.id.progressBar);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputText.getText().toString();
                Log.d("MonApplication", "Envoi de la requête à l'API avec le texte : " + input);
                showProgressBar(); // Affichez la barre de progression avant de démarrer la tâche
                new ChatTask().execute(input);
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private class ChatTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String json = String.format("{\"prompt\":\"%s\", \"temperature\": 0.1, \"max_tokens\": 250}", params[0]);


            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    //.addHeader("OpenAI-Context", "gpt-3.5-turbo")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MonApplication", "Statut de la réponse : " + response.code());
                    return response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            hideProgressBar();
            if (result != null) {
                try {
                    Log.d("MonApplication", "Réponse JSON : " + result);
                    JsonParser parser = new JsonParser();
                    JsonObject jsonResponseObject = parser.parse(result).getAsJsonObject();

                    if (jsonResponseObject.has("choices")) {
                        String text = jsonResponseObject.getAsJsonArray("choices")
                                .get(0) // Supposons qu'il y a une seule réponse
                                .getAsJsonObject()
                                .get("text")
                                .getAsString();

                        // Ajoutez ces logs pour déboguer
                        Log.d("MonApplication", "Texte avant suppression des caractères de nouvelle ligne : " + text);

                        // Supprimer les caractères de nouvelle ligne du début de la chaîne
                        text = text.replaceAll("^\\s+", "");

                        // Ajoutez ces logs pour déboguer
                        Log.d("MonApplication", "Texte après suppression des caractères de nouvelle ligne : " + text);

                        responseText.setText(text);
                    } else {
                        responseText.setText("Réponse JSON mal formée");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    responseText.setText("Erreur de traitement de la réponse");
                }
            } else {
                Log.d("MonApplication", "Erreur de l'API");
            }
        }
    }
}
*/
package com.example.nutriia1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private EditText inputText;
    private Button sendButton;
    private TextView responseText;

    private static final String API_KEY = "sk-proj-X90gz8OrTVHYwsr9L0lZT3BlbkFJdTaevN3P70WHaiGsbulv";
    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputText = findViewById(R.id.inputText);
        sendButton = findViewById(R.id.sendButton);
        responseText = findViewById(R.id.responseText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputText.getText().toString();
                Log.d("MonApplication", "Envoi de la requête à l'API avec le texte : " + input);
                new ChatTask().execute(input);
            }
        });
    }

    private class ChatTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String json = String.format("{\"prompt\":\"%s\", \"temperature\": 0.1, \"max_tokens\": 250}", params[0]);

            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    Log.d("MonApplication", "Réponse de l'API : " + responseBody);
                    return responseBody;
                } else {
                    Log.e("MonApplication", "Erreur de l'API. Code de réponse : " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MonApplication", "Erreur lors de la requête à l'API : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JsonObject jsonResponseObject = JsonParser.parseString(result).getAsJsonObject();
                    if (jsonResponseObject.has("choices")) {
                        String text = jsonResponseObject.getAsJsonArray("choices")
                                .get(0)
                                .getAsJsonObject()
                                .get("text")
                                .getAsString()
                                .replaceAll("^\\s+", "");
                        responseText.setText(text);
                    } else {
                        responseText.setText("Réponse JSON mal formée");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    responseText.setText("Erreur de traitement de la réponse");
                }
            } else {
                Log.d("MonApplication", "Erreur de l'API");
            }
        }
    }
}
