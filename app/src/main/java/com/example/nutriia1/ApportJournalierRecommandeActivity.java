package com.example.nutriia1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApportJournalierRecommandeActivity extends AppCompatActivity {

    private EditText poidsEditText, tailleEditText, ageEditText;
    private    Spinner sexeSpinner;
    private Button genererButton;
    private TextView resultatTextView;
    private ProgressBar progressBar;

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    // Ajoutez la méthode pour masquer le clavier virtuel
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apport_journalier_recommande);

        poidsEditText = findViewById(R.id.poidsEditText);
        tailleEditText = findViewById(R.id.tailleEditText);
        ageEditText = findViewById(R.id.ageEditText);
        sexeSpinner = (Spinner) findViewById(R.id.sexeSpinner); // Utilisez l'ID correct pour votre Spinner

        // sexeSpinner = findViewById(R.id.sexeSpinner);
        genererButton = findViewById(R.id.genererButton);
        resultatTextView = findViewById(R.id.resultatTextView);
        progressBar = findViewById(R.id.progressBar);

        genererButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateApportJournalierRecommande();
            }
        });
    }
    private void generateApportJournalierRecommande() {
        String poids = poidsEditText.getText().toString().trim();
        String taille = tailleEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String sexe = sexeSpinner.getSelectedItem().toString();

        // String sexe = sexeSpinner.getText().toString().trim();

        // Vérifier si au moins un champ est rempli
        if (poids.isEmpty() && taille.isEmpty() && age.isEmpty() && sexe.isEmpty()) {
            resultatTextView.setText("Veuillez remplir au moins un champ.");
        } else {
            // Attribuer des valeurs par défaut aux champs vides
            if (poids.isEmpty()) {
                poids = "70"; // Poids moyen par défaut
            }

            if (taille.isEmpty()) {
                taille = "170"; // Taille moyenne par défaut
            }

            if (age.isEmpty()) {
                age = "30"; // Âge moyen par défaut
            }

            if (sexe.isEmpty()) {
                sexe = "Masculin"; // Sexe masculin par défaut
            }

            // Appel de la méthode pour masquer le clavier
            hideKeyboard();


            // Continuer avec le reste du code
          //  String prompt = String.format("Donne-moi les apports journaliers recommandés en grammes et en microgrammes des macronutriments et des micronutriments pour une personne de %s ans, pesant %s kg, mesurant %s cm, de sexe %s.", age, poids, taille, sexe);
            String prompt = String.format("Donne-moi les apports journaliers recommandés en grammes et en microgrammes des macronutriments et des micronutriments" +
                    "ordonne les differents elements dans l ordre suivant, d abord les macronutriments, ensuite les : Vitamines  A,C,D,E,K,B1,B2,B3,B5,B6,B8,B9,B12" +
                    "ensuite les Macro minéraux :  Calcium, Phosphore, Magnesium,Potassium,Sodium,Chlorure, Soufre" +
             //       "et enfin les Oligo-éléments (ou micro-minéraux) : Fer, Zinc, Cuivre,Manganèse,Iode,Sélénium,Fluor,Chrome,Molybdène,Cobalt,Vanadium,Nickel" +
                                        " pour une personne de %s ans, pesant %s kg, mesurant %s cm, de sexe %s.", age, poids, taille, sexe);
            //String prompt = String.format("Donne-moi les apports journaliers recommandés en grammes et en microgrammes" +
            //       " des macronutriments et des micronutriments pour une personne de %s ans, pesant %s kg, mesurant %s cm, de sexe %s, "+ age, poids, taille, sexe);
                //    "ordonne les differents elements dans l ordre suivant : Vitamines  A,C,D,E,K,B1,B2,B3,B5,B6,B8,B9,B12" +
                  //  "Macro minéraux Calcium, Phosphore, Magnesium,Potassium,Sodium,Chlorure, Soufre" +
                  //  "Oligo-éléments (ou micro-minéraux) : Fer, Zinc, Cuivre,Manganèse,Iode,Sélénium,Fluor,Chrome,Molybdène,Cobalt,Vanadium,Nickel" +
                  //  "Ensuite, propose deux exemples de menu journalier qui répondent bien à ces besoins documentés en macronutriments et micronutriments.",

            new ChatGPTTask().execute(prompt);
        }
    }

    /*
    private void generateApportJournalierRecommande() {
        String poids = poidsEditText.getText().toString();
        String taille = tailleEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String sexe = sexeSpinner.getText().toString();

        if (poids.isEmpty() || taille.isEmpty() || age.isEmpty() || sexe.isEmpty()) {
            resultatTextView.setText("Veuillez remplir tous les champs.");
        } else {
            String prompt = String.format("Donne-moi les apports journaliers recommandés en grammes et en microgrammes des macronutriments et des micronutriments pour une personne de %s ans, pesant %s kg, mesurant %s cm, de sexe %s.", age, poids, taille, sexe);
            new ChatGPTTask().execute(prompt);
        }
    }
*/
    private void displayResponse(String result) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonResponseObject = parser.parse(result).getAsJsonObject();

            if (jsonResponseObject.has("choices")) {
                String text = jsonResponseObject.getAsJsonArray("choices")
                        .get(0) // Supposons qu'il y a une seule réponse
                        .getAsJsonObject()
                        .get("text")
                        .getAsString();

                resultatTextView.setText(text);
            } else {
                resultatTextView.setText("Réponse JSON mal formée");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultatTextView.setText("Erreur de traitement de la réponse");
        }
    }

    private class ChatGPTTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            resultatTextView.setText("");
        }

        @Override
        protected String doInBackground(String... params) {
            String prompt = params[0];
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String json = String.format("{\"prompt\":\"%s\", \"temperature\": 0.1, \"max_tokens\": 800}", prompt);

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
                } else {
                    // Gérer les erreurs de réponse ici
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);

            if (result != null) {
                displayResponse(result);
            } else {
                resultatTextView.setText("Erreur de l'API");
            }
        }
    }
}
