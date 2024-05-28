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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.DishCompositionActivity;
import com.nutriia.nutriia.adapters.DishSuggestionAdapter;
import com.nutriia.nutriia.network.APISend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DishSuggestions extends Fragment {

    private static final List<Dish> dishes = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_dish_suggestions, container, false);

        LinearLayout listView = view.findViewById(R.id.dish_suggestions_list);

        if(dishes.isEmpty()) APISend.obtainsNewDish(getActivity(), dishes -> {
            DishSuggestions.dishes.addAll(dishes);
            addDishes(dishes, listView);
        }, DishSuggestions.dishes);
        else addDishes(dishes, listView);

        ImageButton moreDishButton = view.findViewById(R.id.more_dish_suggestions_button);
        moreDishButton.setOnClickListener(v -> {
            showCustomToast("Génération du plat idéal en cours...", Toast.LENGTH_SHORT);

            APISend.obtainsNewDish(getActivity(), dishes -> {
                DishSuggestions.dishes.addAll(dishes);
                addDishes(dishes, listView);
            }, DishSuggestions.dishes);
        });

        return view;
    }

    private void addDishes(List<Dish> dishes, LinearLayout listView) {
        for (Dish dish : dishes) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_component_dish_suggestions, listView, false);

            TextView dishName = itemView.findViewById(R.id.item_content);
            dishName.setText(dish.getName());

            ImageButton imageButton = itemView.findViewById(R.id.item_button);
            imageButton.setOnClickListener(click -> {
                Intent intent = new Intent(getContext(), DishCompositionActivity.class);
                intent.putExtra("DISH_NAME", dish.getName());
                startActivity(intent);
            });

            listView.addView(itemView);
        }

    }

    private void showCustomToast(String message, int duration) {
        getActivity().runOnUiThread(() -> {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, getActivity().findViewById(R.id.toast_layout_root));

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            Toast toast = new Toast(getContext());
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        });
    }
}