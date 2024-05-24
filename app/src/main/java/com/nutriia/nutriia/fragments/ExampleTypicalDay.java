package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.TypicalDay;
import com.nutriia.nutriia.activities.DayAnalysisActivity;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.resources.Translator;

import java.util.ArrayList;
import java.util.List;

public class ExampleTypicalDay extends Fragment implements APIResponseRDA {

    private View breakfastView;
    private View lunchView;
    private View dinnerView;
    private View snackView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_exemple_typical_day, container, false);

        breakfastView = view.findViewById(R.id.breakfast);
        lunchView = view.findViewById(R.id.lunch);
        dinnerView = view.findViewById(R.id.dinner);
        snackView = view.findViewById(R.id.snack);

        setDishes(breakfastView, new TypicalDay("Petit déjeuner", new ArrayList<>()));
        setDishes(lunchView, new TypicalDay("Déjeuner", new ArrayList<>()));
        setDishes(dinnerView, new TypicalDay("Diner", new ArrayList<>()));
        setDishes(snackView, new TypicalDay("Collations", new ArrayList<>()));

        APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this);

        Button relaunchButton = view.findViewById(R.id.relaunchButton);
        relaunchButton.setOnClickListener(v -> APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this));

        LinearLayout layout = view.findViewById(R.id.linearLayoutMeal);
        layout.setOnClickListener(click -> startActivity(new Intent(getContext(), DayAnalysisActivity.class)));

        return view;
    }

    @Override
    public void onAPIRDAResponse() {
        APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this);
    }

    private void setTypicalDy(List<TypicalDay> dishes) {
        for (TypicalDay typicalDay : dishes) {
            switch (typicalDay.getName()) {
                case "breakfast":
                    setDishes(breakfastView, typicalDay);
                    break;
                case "lunch":
                    setDishes(lunchView, typicalDay);
                    break;
                case "dinner":
                    setDishes(dinnerView, typicalDay);
                    break;
                case "snack":
                    setDishes(snackView, typicalDay);
                    break;
            }
        }
    }

    private void setDishes(View view, TypicalDay typicalDay) {
        TextView textView = view.findViewById(R.id.textMenuType);
        TextView editText = view.findViewById(R.id.textDish);
        textView.setText(Translator.translate(typicalDay.getName()));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typicalDay.getDishes().size(); i++) {
            Dish dish = typicalDay.getDishes().get(i);
            stringBuilder.append(dish.getName()).append(i == typicalDay.getDishes().size()-1 ? "" : "\n");
        }
        editText.setText(stringBuilder.toString());
    }
}