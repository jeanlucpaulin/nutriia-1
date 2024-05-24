package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.MealsAdapter;
import com.nutriia.nutriia.network.APISend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRealDay extends Fragment {

    List<Meal> meals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_my_real_day, container, false);

        meals = new ArrayList<>();
        // Exemple of a list of meals
        //TODO : DELETE THIS EXAMPLE
        meals.add(new Meal("Breakfast"));
        meals.add(new Meal("Lunch"));
        meals.add(new Meal("Dinner"));
        meals.add(new Meal("Snack"));

        TextView breakfastTextView = view.findViewById(R.id.breakfast).findViewById(R.id.textView);
        breakfastTextView.setText(meals.get(0).getName());

        TextView lunchTextView = view.findViewById(R.id.lunch).findViewById(R.id.textView);
        lunchTextView.setText(meals.get(1).getName());

        TextView dinnerTextView = view.findViewById(R.id.dinner).findViewById(R.id.textView);
        dinnerTextView.setText(meals.get(2).getName());

        TextView snackTextView = view.findViewById(R.id.snack).findViewById(R.id.textView);
        snackTextView.setText(meals.get(3).getName());

        Button validateButton = view.findViewById(R.id.validateButton);

        validateButton.setOnClickListener(v -> {
            Map<String, String> userInput = new HashMap<>();
            for(int viewId : new int[] {R.id.breakfast, R.id.lunch, R.id.dinner, R.id.snack}) {
                TextView textView = view.findViewById(viewId).findViewById(R.id.textView);
                EditText editText = view.findViewById(viewId).findViewById(R.id.editText);
                userInput.put(textView.getText().toString().toLowerCase(), editText.getText().toString());
            }

            APISend.sendValidateDay(getActivity(), userInput);
        });

        return view;
    }
}
