package com.nutriia.nutriiaemf.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nutriia.nutriiaemf.models.Nutrient;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.resources.Translator;

/**
 * Create a fragment for a nutrient (Recommended Daily Amount)
 */
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
