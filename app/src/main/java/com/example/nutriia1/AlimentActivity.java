package com.example.nutriia1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutriia1.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlimentActivity extends AppCompatActivity {

    private EditText alimentEditText;
    private Button genererTableauButton;
    private TextView reponseTextView;
    private ScrollView scrollView;
    private ProgressBar progressBar;

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliment);

        alimentEditText = findViewById(R.id.alimentEditText);
        genererTableauButton = findViewById(R.id.genererTableauButton);
        reponseTextView = findViewById(R.id.reponseTextView);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        genererTableauButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alimentChoisi = alimentEditText.getText().toString();

                // Fermer le clavier après avoir cliqué sur le bouton
                fermerClavier();

                ChatGPTTask chatGPTTask = new ChatGPTTask();
                chatGPTTask.execute(alimentChoisi);
            }
        });
    }

    private class ChatGPTTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // Afficher la ProgressBar avant le début de l'opération
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            String input = params[0];
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String json = String.format("{\"prompt\":\"ecrits moi un tableau avec les teneurs en macronutriments et micronutriments , chaque ligne a les trois champs suivants sur la meme ligne  : 1:nom du macro ou micronutriment, 2:teneurs, 3; pourcentage de l apport journalier  et ensuite va a la ligne suivante, n'ecrit pas les titres des colonnes, si le nutriment est absent on ecrit quand meme son nom et on ecrit 0 pour la teneur , l'aliment est   %s\", \"temperature\": 0.1, \"max_tokens\": 450}", input);

            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("OpenAI-Context", "gpt-3.5-turbo")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            // Afficher la ProgressBar avant le début de l'opération
            progressBar.setVisibility(View.GONE);
            if (result != null) {
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonResponseObject = parser.parse(result).getAsJsonObject();

                    if (jsonResponseObject.has("choices")) {
                        String text = jsonResponseObject.getAsJsonArray("choices")
                                .get(0)
                                .getAsJsonObject()
                                .get("text")
                                .getAsString();

                        reponseTextView.setText(text);
                    } else {
                        reponseTextView.setText("Réponse JSON mal formée");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    reponseTextView.setText("Erreur de traitement de la réponse");
                }
            } else {
                reponseTextView.setText("Erreur de l'API");
            }
        }
    }
    private void fermerClavier() {
        View view = getWindow().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
