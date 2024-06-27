package com.nutriia.nutriiaemf.activities;

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

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FoodCompositionAdapter;
import com.nutriia.nutriiaemf.network.APISend;

import java.util.ArrayList;

public class DishRecipeActivity extends AppCompatActivity {

    public ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dish_recipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activty_dish_composition), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));

        loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);

        TextView headerBackTitle = findViewById(R.id.title);
        headerBackTitle.setText("Recette du plat");

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(click -> finish());

        String dishName = getIntent().getStringExtra("DISH_NAME");
        if (dishName != null && !dishName.isEmpty()) {
            TextView dishNameTextView = findViewById(R.id.dish_name);
            dishNameTextView.setText(dishName);
        }

        RecyclerView recipeRecyclerView = findViewById(R.id.recipeComposition);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodCompositionAdapter recipeAdapter = new FoodCompositionAdapter(new ArrayList<>());
        recipeRecyclerView.setAdapter(recipeAdapter);

        RecyclerView ingredientsRecyclerView = findViewById(R.id.recipeIngredients);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodCompositionAdapter ingredientsAdapter = new FoodCompositionAdapter(new ArrayList<>());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);


        APISend.obtainDishRecipe(this, dishName, recipeList -> {
            recipeAdapter.updateData(recipeList);
            recipeAdapter.notifyDataSetChanged();
            loadingSpinner.setVisibility(View.GONE);
        }, ingredientsList -> {
            ingredientsAdapter.updateData(ingredientsList);
            ingredientsAdapter.notifyDataSetChanged();
            loadingSpinner.setVisibility(View.GONE);
        });
    }
}
