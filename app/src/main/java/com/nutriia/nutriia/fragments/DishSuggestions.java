package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DishSuggestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class DishSuggestions extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_dish_suggestions, container, false);

        List<Dish> dishes = new ArrayList<>();

        dishes.add(new Dish("Poulet grillé avec quinoa"));

        DishSuggestionAdapter adapter = new DishSuggestionAdapter(getContext(), dishes);

        ListView listView = view.findViewById(R.id.dish_suggestions_list);

        listView.setAdapter(adapter);

        return view;
    }
}
