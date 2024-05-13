package com.nutriia.nutriia;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.adapters.MealsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExempleTypicalDayActivity extends AppCompatActivity {
    private List<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.componenent_exemple_typical_day);

        meals = new ArrayList<>();
        // Exemple of a list of meals
        //TODO : DELETE THIS EXAMPLE
        meals.add(new Meal("Breakfast", "1 glass of orange juice 1 bowl of cereals 1 cup of coffee"));
        meals.add(new Meal("Lunch", "1 sandwich 1 apple 1 bottle of water"));
        meals.add(new Meal("Dinner", "1 plate of pasta 1 glass of milk 1 piece of cake"));
        meals.add(new Meal("Snack", "1 yogurt 1 banana 1 cup of tea"));

        GridView mealsListView = findViewById(R.id.gridView);
        MealsAdapter mealsAdapter = new MealsAdapter(this, meals);
        mealsListView.setAdapter(mealsAdapter);
    }
}
