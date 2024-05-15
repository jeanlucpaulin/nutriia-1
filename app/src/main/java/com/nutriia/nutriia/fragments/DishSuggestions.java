package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.DishCompositionActivity;
import com.nutriia.nutriia.adapters.DishSuggestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class DishSuggestions extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_dish_suggestions, container, false);

        List<Dish> dishes = new ArrayList<>();

        dishes.add(new Dish("Poulet grillé avec quinoa et légumes grillés"));
        dishes.add(new Dish("Salade de thon aux haricots et aux légumes"));
        dishes.add(new Dish("Omelette aux légumes et fromage cottage"));


        LinearLayout listView = view.findViewById(R.id.dish_suggestions_list);

        for (Dish dish : dishes) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_component_dish_suggestions, listView, false);

            TextView dishName = itemView.findViewById(R.id.item_content);
            dishName.setText(dish.getName());

            ImageButton imageButton = itemView.findViewById(R.id.item_button);
            imageButton.setOnClickListener(click -> startActivity(new Intent(getContext(), DishCompositionActivity.class)));

            listView.addView(itemView);
        }

        return view;
    }
}
