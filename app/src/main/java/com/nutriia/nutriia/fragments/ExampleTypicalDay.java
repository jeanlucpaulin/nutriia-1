package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.MealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExampleTypicalDay extends Fragment {
    List<Meal> meals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_exemple_typical_day, container, false);

        meals = new ArrayList<>();
        // Exemple of a list of meals
        //TODO : DELETE THIS EXAMPLE
        meals.add(new Meal("Breakfast", "1 glass of orange juice 1 bowl of cereals 1 cup of coffee"));
        meals.add(new Meal("Lunch", "1 sandwich 1 apple 1 bottle of water"));
        meals.add(new Meal("Dinner", "1 plate of pasta 1 glass of milk 1 piece of cake"));
        meals.add(new Meal("Snack", "1 yogurt 1 banana 1 cup of tea"));

        GridView mealsListView = view.findViewById(R.id.gridView);
        MealsAdapter mealsAdapter = new MealsAdapter(getContext(), meals);
        mealsListView.setAdapter(mealsAdapter);

        return view;
    }
}
