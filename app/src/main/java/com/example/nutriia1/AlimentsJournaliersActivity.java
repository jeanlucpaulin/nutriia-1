package com.example.nutriia1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlimentsJournaliersActivity extends AppCompatActivity {

    private EditText editPetitDejeuner, editDejeuner, editDiner, editCollation1, editCollation2;
    private Button genererTableauJournalierButton;
    private TextView reponseJournalierTextView;
    private ProgressBar progressBar;

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments_journaliers);

        editPetitDejeuner = findViewById(R.id.editPetitDejeuner);
        editDejeuner = findViewById(R.id.editDejeuner);
        editDiner = findViewById(R.id.editDiner);
        editCollation1 = findViewById(R.id.editCollation1);

        progressBar = findViewById(R.id.progressBar);

        genererTableauJournalierButton = findViewById(R.id.genererTableauJournalierButton);
        reponseJournalierTextView = findViewById(R.id.reponseJournalierTextView);

        genererTableauJournalierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cacher le clavier après avoir cliqué sur le bouton
                hideKeyboard();
                // Afficher la ProgressBar au début du traitement
                progressBar.setVisibility(View.VISIBLE);

                String petitDejeuner = editPetitDejeuner.getText().toString();
                String dejeuner = editDejeuner.getText().toString();
                String diner = editDiner.getText().toString();
                String collation1 = editCollation1.getText().toString();


                // Appel de la tâche asynchrone sur un thread séparé
                new ChatGPTTask().execute(petitDejeuner, dejeuner, diner, collation1);
            }
        });
    }

    private void displayResponse(String result) {
        // Cacher la ProgressBar lorsque la tâche est terminée
        progressBar.setVisibility(View.GONE);
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonResponseObject = parser.parse(result).getAsJsonObject();

            if (jsonResponseObject.has("choices")) {
                String text = jsonResponseObject.getAsJsonArray("choices")
                        .get(0) // Supposons qu'il y a une seule réponse
                        .getAsJsonObject()
                        .get("text")
                        .getAsString();

                reponseJournalierTextView.setText(text);
            } else {
                reponseJournalierTextView.setText("Réponse JSON mal formée");
            }
        } catch (Exception e) {
            e.printStackTrace();
            reponseJournalierTextView.setText("Erreur de traitement de la réponse");
        }
    }

    private class ChatGPTTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String petitDejeuner = params[0];
            String dejeuner = params[1];
            String diner = params[2];
            String collation1 = params[3];


            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        //    String json = String.format("{\"prompt\":\"Prends une quantite moyenne de 100 grammes pour chacun des Aliments suivants: %s, %s, %s, %s ; Maintenant, écris-moi un tableau avec la somme cumulée exprimée en microgrammes pour chacun des 3 macronutriments et des 21 micronutriments dans ces aliments (prends des valeurs moyennes). Chaque ligne devrait avoir les trois champs suivants sur la même ligne : 1) nom du macro ou micronutriment, 2) somme cumulée exprimée en microgrammesgrammes, 3) pourcentage de l'apport journalier recommandé pour ce nutriment. N'inclus pas les titres des colonnes. Si le nutriment est absent, écris quand même son nom et mets 0 pour la teneur.\", \"temperature\": 0.1, \"max_tokens\": 250}", petitDejeuner, dejeuner, diner, collation1);
            String json = String.format("{\"prompt\":\" les aliments absorbés et leur quantite absornés durant la journée sont les suivants: %s, %s, %s, %s ; " +
                    "si la quantité n'est pas indiquée Prends une quantite moyenne de 100 grammes, écrit un premier paragraphe avec le titre Evaluation de la ration alimentaire journalière pour afficher les aliments consommés et expliquer quels sont les principaux" +
                    " macro et les 5 principaux micro-nutriments qui sont en manque, dans un deuxieme paragraphe avec le titre Recommandations propose une liste de 5 aliments riches dans les macro et micronutriments manquants pour combler ces manques, va a la ligne pour chacun, " +
                    " propose ensuite un menu journalier équilibré en listant le contenu des trois principaux repas, dans toutes les proposition d'aliments ou re repas ne met jamais de fruits de mer ou d'aliments dangereux qui déclenchent souvent des allergies.\", \"temperature\": 0.1, \"max_tokens\": 950}", petitDejeuner, dejeuner, diner, collation1);




            //       String json = String.format("{\"prompt\":\"Prends une quantite moyenne de 100 grammes pour chacun des Aliments consommés aujourd'hui :\\nPetit déjeuner : %s\\nDéjeuner : %s\\nDîner : %s\\nCollation1 : %s\\n\\nMaintenant, écris-moi un tableau avec la somme cumulée exprimée en grammes des 3 macronutriments et des 21 micronutriments pour la journée. Chaque ligne devrait avoir les trois champs suivants sur la même ligne : 1) nom du macro ou micronutriment, 2) somme cumulée exprimée en grammes pour les macronutriments et en microgrammes pour les micronutriments, 3) pourcentage de l'apport journalier recommandé pour ce nutriment. N'inclus pas les titres des colonnes. Si le nutriment est absent, écris quand même son nom et mets 0 pour la teneur.\", \"temperature\": 0.1, \"max_tokens\": 250}", petitDejeuner, dejeuner, diner, collation1);
           /* String json = String.format(
                    "{\"prompt\":\"Calcule la somme totale des nutriments pour une journée en consommant les aliments suivants lors des différents repas :\n"
                            + "Petit déjeuner : %s\nDéjeuner : %s\nDîner : %s\nCollation 1 : %s\n\n"
                            + "Écris un tableau présentant la somme des nutriments pour la journée, classés dans l'ordre suivant : d'abord les 3 macronutriments, puis les vitamines A, C, D, E, K, B1, B2, B3, B5, B6, B8, B9, B12."
                            + " Chaque ligne du tableau doit inclure : le nom du nutriment, la somme cumulée pour la journée (exprimée en grammes ou microgrammes), et le pourcentage de l'AJR."
                            + " N'incluez pas les titres des colonnes, allez à la ligne pour chaque nutriment, même si la quantité est nulle. \n\n"
                            + "Ensuite, rédige un court paragraphe résumant les trois principaux nutriments en déficit et proposez des aliments pour combler ces déficits.\", \"temperature\": 0.1, \"max_tokens\": 900}",
                    petitDejeuner, dejeuner, diner, collation1);
*/

               /*String json = String.format(
                    "{\"prompt\":\"Prends une quantité moyenne de 1000 grammes pour chacun des aliments suivants consommés lors des differents repas de la journée:\\n"
                            + "Petit déjeuner : %s\\nDéjeuner : %s\\nDîner : %s\\nCollation 1 : %s\\n\\n"
                            + "évalue la quantité moyenne de chaque nutriment en microgrammes de chacun de ces aliments et  calcule la somme de ces quantités nutriment par nutriment pour la journée entière "
                    //        +"et écris un tableau avec un nutriment par ligne dans l ordre suivant, d abord les 3 macronutriments, ensuite les : Vitamines  A,C,D,E,K,B1,B2,B3,B5,B6,B8,B9,B12, ensuite les : Macro-minéraux Calcium,Phosphore,Magnesium,Potassium,Sodium,Chlorure,Soufre"
                     //       + " ensuite les : Macro-minéraux Calcium,Phosphore,Magnesium,Potassium,Sodium,Chlorure,Soufre, "
                    //        +" ensuite les : Oligo-éléments  Fer,Zinc,Cuivre,Manganèse,Iode,Sélénium,Fluor,Chrome,Molybdène,Cobalt,Vanadium,Nickel"
                            + "ecrits un tableau, Chaque ligne de ce tableau doit avoir les trois champs suivants séparés par une virgule sur la même ligne : "
                            + "1. nom du macro ou micronutriment, 2. somme des quantités cumulée pour la journée entière que tu as évaluée exprimée en microgrammes, "
                            + "3. pourcentage de l AJR (apport journalier recommandé) pour ce nutriment. N inclus pas les titres des colonnes, "
                            + "va a la ligne pour chaque nutriment, Si tu determines que le nutriment est totalement absent des aliments de la journée, écris quand même son nom et mets 0 pour la teneur.\\n\\n"
                            + "a la suite, écris un tres court paragraphe résumant les 3 principaux nutriments qui sont le plus en déficit et propose ensuite des aliments pour combler ces déficits."
                            + "\", \"temperature\": 0.1, \"max_tokens\": 900}",
                    petitDejeuner, dejeuner, diner, collation1);
*/
           /* String json = String.format(
                    "{\"prompt\":\"Prends une quantité moyenne de 1000 grammes pour chacun des aliments suivants consommés lors des différents repas de la journée :\\n"
                            + "Petit déjeuner : %s\\nDéjeuner : %s\\nDîner : %s\\nCollation 1 : %s\\n\\n"
                            + "Évalue la quantité moyenne de chaque nutriment en microgrammes de chacun de ces aliments et calcule la somme de ces quantités nutriment par nutriment pour la journée entière. "
                            + "Écris un tableau avec chaque ligne ayant les trois champs suivants, séparés par une virgule : "
                            + "1. Nom du macro ou micronutriment, 2. Quantité cumulée estimée pour la journée en microgrammes, "
                            + "3. Pourcentage de l'AJR (apport journalier recommandé) pour ce nutriment. N'inclus pas les titres des colonnes, va à la ligne pour chaque nutriment. "
                            + "Si tu détermines que le nutriment est totalement absent des aliments de la journée, écris quand même son nom et mets 0 pour la teneur.\\n\\n"
                            + "Ensuite, écris un très court paragraphe résumant les 3 principaux nutriments qui sont le plus en déficit et propose ensuite des aliments pour combler ces déficits."
                            + "\", \"temperature\": 0.1, \"max_tokens\": 900}",
                    petitDejeuner, dejeuner, diner, collation1);
*/
            // Ajout de la ligne de débogage
            System.out.println("JSON envoyé à l'API : " + json);

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
                    // Ajouter des informations de débogage ici
                    System.out.println("Réponse non réussie. Code : " + response.code());
                    System.out.println("Corps de la réponse : " + response.body().string());

                    // Si la limite de taux est atteinte, attendez 20 secondes avant de réessayer
                    if (response.code() == 429) {
                        System.out.println("Limite de taux atteinte. Attendez 20 secondes avant de réessayer.");
                        Thread.sleep(20000); // Pause de 20 secondes
                        // Réessayez la requête après la pause
                        return doInBackground(params);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Ajouter des informations de débogage ici
                System.out.println("Exception lors de l'appel de l'API : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                displayResponse(result);
            } else {
                reponseJournalierTextView.setText("Le systeme distant n'est pas disponible, merci de reessayer ultérieurement");
            }
        }

    }
    // Fonction pour cacher le clavier
    private void hideKeyboard() {
        View view = getWindow().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
