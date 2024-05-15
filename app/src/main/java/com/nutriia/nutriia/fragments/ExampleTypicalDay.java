package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.DayAnalysisActivity;
import com.nutriia.nutriia.activities.FoodCompositionActivity;
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

        //TODO : DELETE THIS EXAMPLE
        meals.add(new Meal("Petit déjeuner", "1 glass of orange juice 1 bowl of cereals 1 cup of coffee"));
        meals.add(new Meal("Déjeuner", "1 sandwich 1 apple 1 bottle of water"));
        meals.add(new Meal("Diner", "1 plate of pasta 1 glass of milk 1 piece of cake"));
        meals.add(new Meal("Collations", "1 yogurt 1 banana 1 cup of tea"));

        LinearLayout mealLayout = view.findViewById(R.id.mealLayout);
        for (int i = 0; i < meals.size(); i++) {
            LinearLayout row = (LinearLayout) mealLayout.getChildAt(i / 2);
            View mealView = ((ViewGroup) row).getChildAt(i % 2);
            TextView textView = mealView.findViewById(R.id.textView);
            textView.setText(meals.get(i).getName());
        }

        Button relaunchButton = view.findViewById(R.id.relaunchButton);
        relaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout mealLayout = view.findViewById(R.id.mealLayout);
                for (int i = 0; i < meals.size(); i++) {
                    LinearLayout row = (LinearLayout) mealLayout.getChildAt(i / 2);
                    View mealView = ((ViewGroup) row).getChildAt(i % 2);
                    TextView textView = mealView.findViewById(R.id.editText);
                    textView.setText(meals.get(i).getContent());
                }
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayoutMeal);
        layout.setOnClickListener(click -> startActivity(new Intent(getContext(), DayAnalysisActivity.class)));

        return view;
    }
}
