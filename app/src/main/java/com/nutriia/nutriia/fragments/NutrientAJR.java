package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.resources.Translator;

public class NutrientAJR extends Fragment {

    private Nutrient nutrient;

    public NutrientAJR() {
        nutrient = new Nutrient("?", 0, "?");
    }

    public NutrientAJR(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_recommanded_daily_amount, container, false);

        TextView textViewName = view.findViewById(R.id.name);
        textViewName.setText(Translator.translate(nutrient.getName()));

        TextView textViewAmount = view.findViewById(R.id.amount);
        textViewAmount.setText(String.valueOf(nutrient.getValue()));

        TextView textViewUnit = view.findViewById(R.id.unit);
        textViewUnit.setText(nutrient.getUnit());

        return view;
    }


}
