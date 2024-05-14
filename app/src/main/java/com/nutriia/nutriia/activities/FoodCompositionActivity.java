package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;

import java.util.ArrayList;
import java.util.List;


public class FoodCompositionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_composition);

        TextView headerBackTitle = findViewById(R.id.title);
        headerBackTitle.setText("Composition \nd'un aliment");

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(click -> finish());


        RecyclerView macroNutrientsRecyclerView = findViewById(R.id.macronutrients);
        macroNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> macroNutrients = new ArrayList<>();
        macroNutrients.add("Protein");
        macroNutrients.add("Carbs");
        macroNutrients.add("Fat");

        FoodCompositionAdapter macroNutrientAdapter = new FoodCompositionAdapter(macroNutrients);
        macroNutrientsRecyclerView.setAdapter(macroNutrientAdapter);

        RecyclerView microNutrientsRecyclerView = findViewById(R.id.micronutrients);
        microNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> microNutrients = new ArrayList<>();
        microNutrients.add("Vitamin A");
        microNutrients.add("Vitamin B");
        microNutrients.add("Vitamin C");

        FoodCompositionAdapter microNutrientAdapter = new FoodCompositionAdapter(microNutrients);
        microNutrientsRecyclerView.setAdapter(microNutrientAdapter);
    }
}
