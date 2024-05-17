package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.FoodCompositionActivity;

public class FoodComposition extends Fragment {

    private EditText editTextPlat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_food_composition, container, false);

        editTextPlat = view.findViewById(R.id.editplat);

        LinearLayout layout = view.findViewById(R.id.linearLayoutComposition);
        layout.setOnClickListener(click -> {
            String foodName = editTextPlat.getText().toString().trim();
            if (!foodName.isEmpty()) {
                Intent intent = new Intent(getContext(), FoodCompositionActivity.class);
                intent.putExtra("food_name", foodName);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Veuillez saisir un nom d'aliment", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
