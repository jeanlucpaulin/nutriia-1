package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.DayAnalysisActivity;
import com.nutriia.nutriia.network.APISend;

import java.util.List;

public class ExampleTypicalDay extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_exemple_typical_day, container, false);

        TextView breakfastView = view.findViewById(R.id.breakfast).findViewById(R.id.textView);
        TextView lunchView = view.findViewById(R.id.lunch).findViewById(R.id.textView);
        TextView dinnerView = view.findViewById(R.id.dinner).findViewById(R.id.textView);
        TextView snackView = view.findViewById(R.id.snack).findViewById(R.id.textView);

        breakfastView.setText("Petit déjeuner");
        lunchView.setText("Déjeuner");
        dinnerView.setText("Diner");
        snackView.setText("Collations");

        Button relaunchButton = view.findViewById(R.id.relaunchButton);
        relaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APISend.obtainsTypicalDay(getActivity(), dishes -> {
                    if (!dishes.isEmpty()) {
                        breakfastView.setText(dishes.get(0).getName());
                        lunchView.setText(dishes.get(1).getName());
                        dinnerView.setText(dishes.get(2).getName());
                        snackView.setText(dishes.get(3).getName());
                    }
                });
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayoutMeal);
        layout.setOnClickListener(click -> startActivity(new Intent(getContext(), DayAnalysisActivity.class)));

        return view;
    }
}