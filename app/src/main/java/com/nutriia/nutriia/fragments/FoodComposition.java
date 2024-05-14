package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.FoodCompositionActivity;

public class FoodComposition extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_food_composition, container, false);

        LinearLayout layout = view.findViewById(R.id.linearLayoutComposition);
        layout.setOnClickListener(click -> startActivity(new Intent(getContext(), FoodCompositionActivity.class)));

        return view;
    }
}
