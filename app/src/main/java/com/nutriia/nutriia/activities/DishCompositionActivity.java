package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.resources.Translator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DishCompositionActivity extends AppCompatActivity {

    public ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dish_composition);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activty_dish_composition), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));

        loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);

        TextView headerBackTitle = findViewById(R.id.title);
        headerBackTitle.setText("Composition \ndu plat");

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(click -> finish());

        String dishName = getIntent().getStringExtra("DISH_NAME");
        APISend.obtainsDishComposition(this, dishName, this::updateUI);
    }

    private void updateUI(Dish dish) {

        TextView dishName = findViewById(R.id.dish_name);
        TextView dishCalories = findViewById(R.id.dish_calorie);

        dishName.setText(dish.getName());
        dishCalories.setText(String.valueOf(dish.getCalories()) + " Kcal / 100g");

        // Dish Composition
        RecyclerView dishCompositionRecyclerView = findViewById(R.id.dishCompostion);
        dishCompositionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> compositionList = new ArrayList<>();
        JSONObject micronutrients = dish.getMicronutrients();
        JSONObject macronutrients = dish.getMacronutrients();

        Iterator<String> keys = macronutrients.keys();
        compositionList.add("----- MACRO - NUTRIMENTS: -----");
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                JSONObject nutrient = macronutrients.getJSONObject(key);
                compositionList.add(Translator.translate(key) + ": " + nutrient.getInt("value") + " " + nutrient.getString("unit"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        keys = micronutrients.keys();
        compositionList.add("----- MICRO - NUTRIMENTS: -----");
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                JSONObject nutrient = micronutrients.getJSONObject(key);
                compositionList.add(Translator.translate(key) + ": " + nutrient.getInt("value") + " " + nutrient.getString("unit"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        FoodCompositionAdapter compositionAdapter = new FoodCompositionAdapter(compositionList);
        dishCompositionRecyclerView.setAdapter(compositionAdapter);

        // Recipe
        RecyclerView RecipeRecyclerView = findViewById(R.id.recipeComposition);
        RecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> recipeList = new ArrayList<>();
        recipeList.add(dish.getRecipe());

        FoodCompositionAdapter recipeAdapter = new FoodCompositionAdapter(recipeList);
        RecipeRecyclerView.setAdapter(recipeAdapter);
    }
}
