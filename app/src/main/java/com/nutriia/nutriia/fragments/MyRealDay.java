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

public class MyRealDay extends Fragment {

    List<Meal> meals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_my_real_day, container, false);

        meals = new ArrayList<>();
        // Exemple of a list of meals
        //TODO : DELETE THIS EXAMPLE
        meals.add(new Meal("Breakfast"));
        meals.add(new Meal("Lunch"));
        meals.add(new Meal("Dinner"));
        meals.add(new Meal("Snack"));

        /*
        GridView mealsListView = view.findViewById(R.id.gridView);
        MealsAdapter mealsAdapter = new MealsAdapter(getContext(), meals, true);
        mealsListView.setAdapter(mealsAdapter);
        */
        return view;
    }
}
