package com.nutriia.nutriia;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nutriia.nutriia.adapters.ButtonObjectifAdapter;
import java.util.ArrayList;
import java.util.List;

public class HealthInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);

        TextView headerBackTitle = (TextView) findViewById(R.id.title);
        headerBackTitle.setText("Informations \nsanté");

        // Spinner pour l'âge
        Spinner spinnerAge = findViewById(R.id.spinnerAge);
        List<String> ages = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            ages.add(Integer.toString(i));
        }
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ages);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(ageAdapter);

        // Spinner pour le genre
        Spinner spinnerGender = findViewById(R.id.spinnerSexe);
        List<String> genders = new ArrayList<>();
        genders.add("Homme");
        genders.add("Femme");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Spinner pour le niveau d'activité
        Spinner spinnerActivityLevel = findViewById(R.id.spinnerActivite);
        List<String> activityLevels = new ArrayList<>();
        activityLevels.add("Faible");
        activityLevels.add("Moyen");
        activityLevels.add("Élevé");
        ArrayAdapter<String> activityLevelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityLevels);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(activityLevelAdapter);

        // Spinner pour le niveau de progression souhaité
        Spinner spinnerProgression = findViewById(R.id.spinnerProgression);
        List<String> progressions = new ArrayList<>();
        progressions.add("Faible");
        progressions.add("Moyen");
        progressions.add("Élevé");
        ArrayAdapter<String> progressionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, progressions);
        progressionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgression.setAdapter(progressionAdapter);


        // Récupérez une référence au bouton "Valider"
        Button submitButton = (Button) findViewById(R.id.confirm);

        // Récupérez une référence aux EditTexts pour la taille et le poids
        EditText editTaille = findViewById(R.id.editTaille);
        EditText editPoids = findViewById(R.id.editPoids);

        // Ajoutez un écouteur d'événements OnClickListener au bouton
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérez les informations des Spinners et des EditTexts
                String age = spinnerAge.getSelectedItem().toString();
                String gender = spinnerGender.getSelectedItem().toString();
                String activityLevel = spinnerActivityLevel.getSelectedItem().toString();
                String progression = spinnerProgression.getSelectedItem().toString();
                String taille = editTaille.getText().toString();
                String poids = editPoids.getText().toString();

                // Affichez les informations
                String message =
                        "Taille: " + taille + " cm\n" +
                        "Poids: " + poids + " kg" +
                        "Age: " + age + "\n" +
                        "Genre: " + gender + "\n" +
                        "Niveau d'activité: " + activityLevel + "\n" +
                        "Niveau de progression souhaité: " + progression + "\n";

                Toast.makeText(HealthInformationActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
