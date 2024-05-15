package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.ItemRDA;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.ItemRDAAdapter;
import com.nutriia.nutriia.interfaces.IItemRDA;

import java.util.ArrayList;
import java.util.List;

public class RecommendedDailyAmount extends Fragment {
    private List<IItemRDA> macronutrients;
    private List<IItemRDA> micronutrients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_recommanded_daily_amount, container, false);

        macronutrients = new ArrayList<>();
        macronutrients.add(new ItemRDA("Proteins", "g", 50));
        macronutrients.add(new ItemRDA("Carbohydrates", "g", 300));
        macronutrients.add(new ItemRDA("Lipids", "g", 70));

        micronutrients = new ArrayList<>();
        micronutrients.add(new ItemRDA("Vitamin A", "µg", 800));
        micronutrients.add(new ItemRDA("Vitamin C", "mg", 110));
        micronutrients.add(new ItemRDA("Vitamin D", "µg", 5));

        RecyclerView macronutrientsListView = view.findViewById(R.id.macronutrients_list);
        RecyclerView micronutrientsListView = view.findViewById(R.id.micronutrients_list);

        macronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        micronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemRDAAdapter macronutrientsAdapter = new ItemRDAAdapter(getContext(), macronutrients);
        ItemRDAAdapter micronutrientsAdapter = new ItemRDAAdapter(getContext(), micronutrients);

        macronutrientsListView.setAdapter(macronutrientsAdapter);
        micronutrientsListView.setAdapter(micronutrientsAdapter);

        return view;
    }
}
