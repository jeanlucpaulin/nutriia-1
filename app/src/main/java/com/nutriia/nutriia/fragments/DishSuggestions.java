package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.DishCompositionActivity;
import com.nutriia.nutriia.adapters.DishSuggestionAdapter;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;
import com.nutriia.nutriia.builders.DishBuilder;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.resources.Settings;
import com.nutriia.nutriia.resources.Translator;
import com.nutriia.nutriia.user.Saver;
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.Date;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DishSuggestions extends Fragment {

    private final List<Dish> dishes;

    public DishSuggestions() {
        dishes = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_dish_suggestions, container, false);

        LinearLayout listView = view.findViewById(R.id.dish_suggestions_list);

        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(getContext());
        String date = Date.getTodayDate();
        String sharedDate = sharedPreferences.getDishSuggestionsDate();
        if(!sharedDate.isEmpty() && date.equals(sharedDate)) {
            dishes.addAll(DishBuilder.buildDish(sharedPreferences));
            Log.d("DishSuggestions", "DishSuggestions: " + dishes.size());
        }
        else {
            Log.d("DishSuggestions", "DishSuggestions: " + sharedDate + " " + date);
        }

        if(this.dishes.isEmpty()) APISend.obtainsNewDish(getActivity(), dishes -> {
            this.dishes.addAll(dishes);
            addDishes(dishes, listView);
        }, this.dishes);
        else addDishes(dishes, listView);

        ImageButton moreDishButton = view.findViewById(R.id.more_dish_suggestions_button);
        moreDishButton.setOnClickListener(v -> {
            moreDishButton.setEnabled(false);
            if(dishes.size() >= Settings.getMaxDishSuggestions())
            {
                showCustomToast("Nombre maximum de générations atteint", Toast.LENGTH_SHORT);
                return;
            }

            showCustomToast("Génération du plat idéal en cours...", Toast.LENGTH_SHORT);

            APISend.obtainsNewDish(getActivity(), dishes -> {
                moreDishButton.setEnabled(true);
                this.dishes.addAll(dishes);
                addDishes(dishes, listView);
            }, this.dishes);
        });

        return view;
    }

    private void addDishes(List<Dish> dishes, LinearLayout listView) {
        for (Dish dish : dishes) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_component_dish_suggestions, listView, false);

            LinearLayout linearLayoutComposition = itemView.findViewById(R.id.linearLayoutCompositionDish);
            LinearLayout macroMicroList = itemView.findViewById(R.id.macroMicroListDish);
            LinearLayout closeList = itemView.findViewById(R.id.closeList);
            TextView foodCalorie = itemView.findViewById(R.id.food_calorie_Dish);

            macroMicroList.setVisibility(View.GONE);
            closeList.setVisibility(View.GONE);
            foodCalorie.setVisibility(View.GONE);

            linearLayoutComposition.setOnClickListener(v -> {
                linearLayoutComposition.setVisibility(View.GONE);
                macroMicroList.setVisibility(View.VISIBLE);
                closeList.setVisibility(View.VISIBLE);
                foodCalorie.setVisibility(View.VISIBLE);
            });

            closeList.setOnClickListener(v -> {
                macroMicroList.setVisibility(View.GONE);
                closeList.setVisibility(View.GONE);
                foodCalorie.setVisibility(View.GONE);
                linearLayoutComposition.setVisibility(View.VISIBLE);
            });

            TextView dishName = itemView.findViewById(R.id.item_content);
            dishName.setText(dish.getName());

            ImageButton imageButton = itemView.findViewById(R.id.item_button);
            imageButton.setOnClickListener(click -> {
                Intent intent = new Intent(getContext(), DishCompositionActivity.class);
                intent.putExtra("DISH_NAME", dish.getName());
                startActivity(intent);
            });

            // Get the RecyclerViews
            RecyclerView macronutrientsRecyclerView = itemView.findViewById(R.id.macronutrients);
            RecyclerView micronutrientsRecyclerView = itemView.findViewById(R.id.micronutrients);

            // Set their LayoutManagers
            macronutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            micronutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            APISend.obtainsDishComposition(getActivity(), dish.getName(), obtainedDish -> {

                // Check if the dish composition data is already saved locally
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DishComposition", Context.MODE_PRIVATE);
                String savedData = sharedPreferences.getString(dish.getName(), null);

                // Create the lists for the composition
                List<String> macronutrientsList = new ArrayList<>();
                List<String> micronutrientsList = new ArrayList<>();

                // Get the nutrients from the Dish object
                JSONObject macronutrients;
                JSONObject micronutrients;

                if (savedData != null) {
                    // Use the saved data
                    try {
                        JSONObject jsonObject = new JSONObject(savedData);
                        Dish savedDish = new Dish(jsonObject);

                        macronutrients = savedDish.getMacronutrients();
                        micronutrients = savedDish.getMicronutrients();
                        foodCalorie.setText(String.valueOf(savedDish.getCalories()) + " Kcal / 100g");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    // Use the data from the API
                    macronutrients = obtainedDish.getMacronutrients();
                    micronutrients = obtainedDish.getMicronutrients();
                    foodCalorie.setText(String.valueOf(obtainedDish.getCalories()) + " Kcal / 100g");
                }

                // Fill the macronutrientsList
                Iterator<String> keys = macronutrients.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        JSONObject nutrient = macronutrients.getJSONObject(key);
                        macronutrientsList.add(Translator.translate(key) + ": " + nutrient.getInt("value") + " " + nutrient.getString("unit"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Fill the micronutrientsList
                keys = micronutrients.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        JSONObject nutrient = micronutrients.getJSONObject(key);
                        micronutrientsList.add(Translator.translate(key) + ": " + nutrient.getInt("value") + " " + nutrient.getString("unit"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Set the adapters
                FoodCompositionAdapter macronutrientsAdapter = new FoodCompositionAdapter(macronutrientsList);
                FoodCompositionAdapter micronutrientsAdapter = new FoodCompositionAdapter(micronutrientsList);
                macronutrientsRecyclerView.setAdapter(macronutrientsAdapter);
                micronutrientsRecyclerView.setAdapter(micronutrientsAdapter);

                // Update the adapters
                ((FoodCompositionAdapter) macronutrientsRecyclerView.getAdapter()).updateData(macronutrientsList);
                ((FoodCompositionAdapter) micronutrientsRecyclerView.getAdapter()).updateData(micronutrientsList);

            });
            listView.addView(itemView);
        }

        Saver.saveDishSuggestions(getContext(), this.dishes);
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