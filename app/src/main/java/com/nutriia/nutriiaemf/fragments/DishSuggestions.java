package com.nutriia.nutriiaemf.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriiaemf.models.Dish;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.activities.DishRecipeActivity;
import com.nutriia.nutriiaemf.adapters.FoodCompositionAdapter;
import com.nutriia.nutriiaemf.builders.DishBuilder;
import com.nutriia.nutriiaemf.network.APISend;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.resources.Translator;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;
import com.nutriia.nutriiaemf.utils.Date;
import com.nutriia.nutriiaemf.user.Saver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DishSuggestions extends AppFragment {

    private final List<Dish> dishes;

    private Context  context;

    public DishSuggestions() {
        dishes = new ArrayList<>();
    }

    private Activity getActivity() {
        return (Activity) context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_dish_suggestions, frameLayout, false);

        LinearLayout listView = view.findViewById(R.id.dish_suggestions_list);

        // Get the dishes from the UserSharedPreferences
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(getContext());
        String date = Date.getTodayDate();
        String sharedDate = sharedPreferences.getDishSuggestionsDate();
        if (!sharedDate.isEmpty() && date.equals(sharedDate)) {
            dishes.addAll(DishBuilder.buildDish(sharedPreferences));
            Log.d("DishSuggestions", "DishSuggestions: " + dishes.size());
        } else {
            Log.d("DishSuggestions", "DishSuggestions: " + sharedDate + " " + date);
        }

        if (this.dishes.isEmpty()) {
            APISend.obtainsNewDish(getActivity(), newDishes -> {
                this.dishes.addAll(newDishes);
                addDishes(this.dishes, listView);
            }, this.dishes);
        } else {
            addDishes(this.dishes, listView);
        }

        ImageButton moreDishButton = view.findViewById(R.id.more_dish_suggestions_button);
        moreDishButton.setOnClickListener(v -> {
            moreDishButton.setEnabled(false);
            if (dishes.size() >= Settings.getMaxDishSuggestions()) {
                showCustomToast("Nombre maximum de générations atteint", Toast.LENGTH_SHORT);
                return;
            }

            showCustomToast("Génération du plat idéal en cours...", Toast.LENGTH_SHORT);

            APISend.obtainsNewDish(getActivity(), newDishes -> {
                moreDishButton.setEnabled(true);
                if (!newDishes.isEmpty()) {
                    Dish newDish = newDishes.get(0); // Prendre le premier plat de la liste des nouveaux plats
                    this.dishes.add(newDish); // Ajouter uniquement le nouveau plat à la liste des plats existants
                    addDishes(List.of(newDish), listView); // Ajouter uniquement le nouveau plat à la vue
                }
            }, this.dishes);
        });

        frameLayout.addView(view);
    }

    /**
     * Add dishes to the list view
     * @param dishes
     * @param listView
     */
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
                Intent intent = new Intent(getContext(), DishRecipeActivity.class);
                intent.putExtra("DISH_NAME", dish.getName());
                context.startActivity(intent);
            });

            TextView textView = itemView.findViewById(R.id.recipe);
            textView.setOnClickListener(click -> {
                Intent intent = new Intent(getContext(), DishRecipeActivity.class);
                intent.putExtra("DISH_NAME", dish.getName());
                context.startActivity(intent);
            });

            // Get the RecyclerViews
            RecyclerView macronutrientsRecyclerView = itemView.findViewById(R.id.macronutrients);
            RecyclerView micronutrientsRecyclerView = itemView.findViewById(R.id.micronutrients);

            // Set their LayoutManagers
            macronutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            micronutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Set initial empty adapters
            FoodCompositionAdapter macronutrientsAdapter = new FoodCompositionAdapter(new ArrayList<>());
            FoodCompositionAdapter micronutrientsAdapter = new FoodCompositionAdapter(new ArrayList<>());
            macronutrientsRecyclerView.setAdapter(macronutrientsAdapter);
            micronutrientsRecyclerView.setAdapter(micronutrientsAdapter);

            APISend.obtainsDishComposition(getActivity(), dish.getName(), obtainedDish -> {

                // Check if the dish composition data is already saved locally
                if(getActivity() == null) return;
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

                // Sort the macronutrientsList
                macronutrientsList.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        String name1 = s1.split(":")[0];
                        String name2 = s2.split(":")[0];

                        String nonDigitPart1 = name1.replaceAll("\\d", "");
                        String nonDigitPart2 = name2.replaceAll("\\d", "");

                        int nonDigitPartComparison = nonDigitPart1.compareTo(nonDigitPart2);
                        if (nonDigitPartComparison != 0) {
                            return nonDigitPartComparison;
                        } else {
                            String digitPart1 = name1.replaceAll("\\D", "");
                            String digitPart2 = name2.replaceAll("\\D", "");

                            // If there are no digits in the name, compare the names directly
                            if (digitPart1.isEmpty() && digitPart2.isEmpty()) {
                                return name1.compareTo(name2);
                            }

                            // If one name has digits and the other doesn't, the one with digits comes first
                            if (digitPart1.isEmpty()) {
                                return 1;
                            }
                            if (digitPart2.isEmpty()) {
                                return -1;
                            }

                            // If both names have digits, compare the digit parts as integers
                            return Integer.compare(Integer.parseInt(digitPart1), Integer.parseInt(digitPart2));
                        }
                    }
                });

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

                // Sort the micronutrientsList
                micronutrientsList.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        String name1 = s1.split(":")[0];
                        String name2 = s2.split(":")[0];

                        String nonDigitPart1 = name1.replaceAll("\\d", "");
                        String nonDigitPart2 = name2.replaceAll("\\d", "");

                        int nonDigitPartComparison = nonDigitPart1.compareTo(nonDigitPart2);
                        if (nonDigitPartComparison != 0) {
                            return nonDigitPartComparison;
                        } else {
                            String digitPart1 = name1.replaceAll("\\D", "");
                            String digitPart2 = name2.replaceAll("\\D", "");

                            // If there are no digits in the name, compare the names directly
                            if (digitPart1.isEmpty() && digitPart2.isEmpty()) {
                                return name1.compareTo(name2);
                            }

                            // If one name has digits and the other doesn't, the one with digits comes first
                            if (digitPart1.isEmpty()) {
                                return 1;
                            }
                            if (digitPart2.isEmpty()) {
                                return -1;
                            }

                            // If both names have digits, compare the digit parts as integers
                            return Integer.compare(Integer.parseInt(digitPart1), Integer.parseInt(digitPart2));
                        }
                    }
                });

                // Update the adapters
                macronutrientsAdapter.updateData(macronutrientsList);
                micronutrientsAdapter.updateData(micronutrientsList);

            });
            listView.addView(itemView);
        }

        Saver.saveDishSuggestions(getContext(), this.dishes);
    }

    private void showCustomToast(String message, int duration) {
        getActivity().runOnUiThread(() -> {
            LayoutInflater inflater = getActivity().getLayoutInflater();
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
