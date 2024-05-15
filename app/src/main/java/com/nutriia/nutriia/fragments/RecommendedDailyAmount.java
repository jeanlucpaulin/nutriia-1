package com.nutriia.nutriia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.ItemRDA;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.ItemRDAAdapter;
import com.nutriia.nutriia.interfaces.IItemRDA;

import java.util.ArrayList;
import java.util.List;

public class RecommendedDailyAmount extends Fragment {


    private List<Fragment> macronutrients;
    private List<Fragment> micronutrients;

    private AppCompatActivity activity;

    public RecommendedDailyAmount(AppCompatActivity activity) {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_recommanded_daily_amount, container, false);

        macronutrients = new ArrayList<>();
        macronutrients.add(new NutrientAJR(new Nutrient("Proteins", 50, "g")));
        macronutrients.add(new NutrientAJR(new Nutrient("Carbohydrates", 300, "g")));
        macronutrients.add(new NutrientAJR(new Nutrient("Lipids", 70, "g")));

        micronutrients = new ArrayList<>();
        micronutrients.add(new NutrientAJR(new Nutrient("Vitamin A", 800, "µg")));
        micronutrients.add(new NutrientAJR(new Nutrient("Vitamin B12", 200, "µg")));
        micronutrients.add(new NutrientAJR(new Nutrient("Vitamin C", 80, "mg")));

        RecyclerView macronutrientsListView = view.findViewById(R.id.macronutrients_list);
        RecyclerView micronutrientsListView = view.findViewById(R.id.micronutrients_list);

        macronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        micronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemRDAAdapter macronutrientsAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), macronutrients);
        ItemRDAAdapter micronutrientsAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), micronutrients);

        macronutrientsListView.setAdapter(macronutrientsAdapter);
        micronutrientsListView.setAdapter(micronutrientsAdapter);

        return view;
    }
}
