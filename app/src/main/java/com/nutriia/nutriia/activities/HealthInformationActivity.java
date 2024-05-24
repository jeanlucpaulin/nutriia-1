package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthInformationActivity extends AppCompatActivity {

    private int positionGenderSpinner = -1;
    private int positionActivityLevelSpinner = -1;
    private int positionProgressionSpinner = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);

        TextView headerBackTitle = (TextView) findViewById(R.id.title);
        headerBackTitle.setText("Informations \nsanté");

        ImageButton imageButton = (ImageButton) findViewById(R.id.lateral_open);

        imageButton.setOnClickListener(click -> finish());

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
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { positionGenderSpinner = position; }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        List<String> genders = Arrays.asList(getResources().getStringArray(R.array.genders));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Spinner pour le niveau d'activité
        Spinner spinnerActivityLevel = findViewById(R.id.spinnerActivite);
        spinnerActivityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { positionActivityLevelSpinner = position; }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        List<String> activityLevels = Arrays.asList(getResources().getStringArray(R.array.activity_levels));
        ArrayAdapter<String> activityLevelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityLevels);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(activityLevelAdapter);

        // Spinner pour le niveau de progression souhaité
        Spinner spinnerProgression = findViewById(R.id.spinnerProgression);

        spinnerProgression.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                positionProgressionSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        List<String> progressions = Arrays.asList(getResources().getStringArray(R.array.progression_levels));
        ArrayAdapter<String> progressionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, progressions);
        progressionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgression.setAdapter(progressionAdapter);


        // Récupérez une référence au bouton "Valider"
        Button submitButton = (Button) findViewById(R.id.confirm);

        // Récupérez une référence aux EditTexts pour la taille et le poids
        EditText editTaille = findViewById(R.id.editTaille);
        EditText editPoids = findViewById(R.id.editPoids);

        // Ajoutez un écouteur d'événements OnClickListener au bouton
        submitButton.setOnClickListener(click -> {
            String height = editTaille.getText().toString();
            String weight = editPoids.getText().toString();
            String age = spinnerAge.getSelectedItem().toString();
            boolean hasChanged = false;
            UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getApplicationContext());
            if(!height.isEmpty()) {
                try {
                    int heightInt = Integer.parseInt(height);
                    if(userSharedPreferences.getHeight() != heightInt)
                    {
                        hasChanged = true;
                        userSharedPreferences.setHeight(heightInt);
                    }
                } catch (NumberFormatException ignored) {}
            }

            if(!weight.isEmpty()) {
                try {
                    int weightInt = Integer.parseInt(weight);
                    if(userSharedPreferences.getWeight() != weightInt)
                    {
                        hasChanged = true;
                        userSharedPreferences.setWeight(weightInt);
                    }
                } catch (NumberFormatException ignored) {}
            }


            if(!age.isEmpty()) {
                try {
                    int ageInt = Integer.parseInt(age);
                    if(ageInt >= 1 && ageInt <= 100) {
                        if(userSharedPreferences.getAge() != ageInt)
                        {
                            hasChanged = true;
                            userSharedPreferences.setAge(ageInt);
                        }
                    }
                } catch (NumberFormatException ignored) {}
            }

            if(positionGenderSpinner != -1) {
                if(userSharedPreferences.getGender() != positionGenderSpinner)
                {
                    hasChanged = true;
                    userSharedPreferences.setGender(positionGenderSpinner);
                }
            }

            if(positionActivityLevelSpinner != -1) {
                if(userSharedPreferences.getActivityLevel() != positionActivityLevelSpinner)
                {
                    hasChanged = true;
                    userSharedPreferences.setActivityLevel(positionActivityLevelSpinner);
                }
            }

            if(positionProgressionSpinner != -1) {
                if(userSharedPreferences.getProgression() != positionProgressionSpinner)
                {
                    hasChanged = true;
                    userSharedPreferences.setProgression(positionProgressionSpinner);
                }
            }

            if(hasChanged)
            {
                userSharedPreferences.clearRDA();
                userSharedPreferences.clearTypicalDay();
            }

            setResult(RESULT_OK, new Intent());
            finish();

        });

        setFields(editTaille, editPoids, spinnerAge, spinnerGender, spinnerActivityLevel, spinnerProgression);
    }


    private void setFields(EditText editHeight, EditText editWeight, Spinner spinnerAge, Spinner spinnerGender, Spinner spinnerActivityLevel, Spinner spinnerProgression) {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getApplicationContext());
        int height = userSharedPreferences.getHeight();
        int weight = userSharedPreferences.getWeight();
        int age = userSharedPreferences.getAge();
        int gender = userSharedPreferences.getGender();
        int activityLevel = userSharedPreferences.getActivityLevel();
        int progression = userSharedPreferences.getProgression();

        if (height != -1) editHeight.setText(String.valueOf(height));
        if (weight != -1) editWeight.setText(String.valueOf(weight));
        if (age != -1) spinnerAge.setSelection(age - 1);
        if (gender != -1) spinnerGender.setSelection(gender);
        if (activityLevel != -1) spinnerActivityLevel.setSelection(activityLevel);
        if (progression != -1) spinnerProgression.setSelection(progression);
    }

}
