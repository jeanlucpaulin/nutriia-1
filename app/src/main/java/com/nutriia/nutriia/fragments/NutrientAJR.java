package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.resources.Translator;

public class NutrientAJR extends AppFragment {

    private Nutrient nutrient;

    public NutrientAJR(Nutrient nutrient) {
        this.nutrient = nutrient;
    }


    @Override
    public void create(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_recommanded_daily_amount, frameLayout, false);

        TextView textViewName = view.findViewById(R.id.name);
        textViewName.setText(Translator.translate(nutrient.getName()));

        TextView textViewAmount = view.findViewById(R.id.amount);
        textViewAmount.setText(String.valueOf(nutrient.getValue()));

        TextView textViewUnit = view.findViewById(R.id.unit);
        textViewUnit.setText(nutrient.getUnit());

        frameLayout.addView(view);
    }


}
