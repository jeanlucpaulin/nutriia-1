package com.nutriia.nutriia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.ItemRDA;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.ItemRDAAdapter;
import com.nutriia.nutriia.builders.DayBuilder;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.interfaces.IItemRDA;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RecommendedDailyAmount extends Fragment implements APIResponseRDA {


    private final List<Fragment> macronutrients = new ArrayList<>();
    private final List<Fragment> micronutrients = new ArrayList<>();
    private TextView caloriesText;
    private RecyclerView macronutrientsListView;
    private RecyclerView micronutrientsListView;

    private AppCompatActivity activity;

    public RecommendedDailyAmount(AppCompatActivity activity) {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_recommanded_daily_amount, container, false);

        macronutrientsListView = view.findViewById(R.id.macronutrients_list);
        micronutrientsListView = view.findViewById(R.id.micronutrients_list);
        caloriesText = view.findViewById(R.id.energy_amount);

        macronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        micronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));

        APISend.obtainsNewGoalRDA(this.getActivity(), this);

        return view;
    }

    @Override
    public void onAPIRDAResponse() {
        Log.d("API", "onAPIRDAResponse");
        Day day = new DayBuilder().buildOnlyWithGoal(UserSharedPreferences.getInstance(getContext()));
        List<Fragment> macroFragments = new ArrayList<>();
        List<Fragment> microFragments = new ArrayList<>();

        for(Nutrient nutrient : day.getMacroNutrients().values()) {
            macroFragments.add(new NutrientAJR(nutrient));
        }
        for(Nutrient nutrient : day.getMicroNutrients().values()) {
            microFragments.add(new NutrientAJR(nutrient));
        }

        ItemRDAAdapter macroAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), macroFragments);
        ItemRDAAdapter microAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), microFragments);

        macronutrients.clear();
        macronutrients.addAll(macroFragments);
        micronutrients.clear();
        micronutrients.addAll(microFragments);

        macronutrientsListView.setAdapter(macroAdapter);
        micronutrientsListView.setAdapter(microAdapter);

        caloriesText.setText(String.valueOf(day.getCalorie().getValue()));
    }
}
