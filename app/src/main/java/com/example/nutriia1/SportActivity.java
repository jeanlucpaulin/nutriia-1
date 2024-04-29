package com.example.nutriia1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        // Récupérez le Spinner à partir du layout
        Spinner sportSpinner = findViewById(R.id.sportSpinner);

        // Créez un tableau de chaînes contenant les options de sport
        String[] sports = {"Cyclisme", "Football", "Jogging", "Musculation", "Fitness", "Danse", "Marche"};

        // Créez un adaptateur pour le Spinner en utilisant les options de sport
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sports);

        // Spécifiez le layout pour la liste déroulante lorsque déroulé
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Appliquez l'adaptateur au Spinner
        sportSpinner.setAdapter(adapter);
    }
}
