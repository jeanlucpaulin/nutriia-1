package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;

import java.util.ArrayList;
import java.util.List;

public class DishCompositionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_composition);

        TextView headerBackTitle = findViewById(R.id.title);
        headerBackTitle.setText("Composition \ndu plat");

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(click -> finish());

        // Dish Composition

        RecyclerView dishCompositionRecyclerView = findViewById(R.id.dishCompostion);
        dishCompositionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> Nutrients = new ArrayList<>();
        Nutrients.add("Protein");
        Nutrients.add("Carbs");
        Nutrients.add("Fat");

        FoodCompositionAdapter dishAdapter = new FoodCompositionAdapter(Nutrients);
        dishCompositionRecyclerView.setAdapter(dishAdapter);

        // Food Composition

        RecyclerView foodListRecyclerView = findViewById(R.id.foodComposition);
        foodListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> foodComposition = new ArrayList<>();
        foodComposition.add("Vitamin A");
        foodComposition.add("Vitamin B");
        foodComposition.add("Vitamin C");

        FoodCompositionAdapter foodAdapter = new FoodCompositionAdapter(foodComposition);
        foodListRecyclerView.setAdapter(foodAdapter);


        // Recipe

        RecyclerView RecipeRecyclerView = findViewById(R.id.recipeComposition);
        RecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> recipeList = new ArrayList<>();
        recipeList.add("Vitamin A");
        recipeList.add("Vitamin B");
        recipeList.add("Vitamin C");

        FoodCompositionAdapter recipeAdapter = new FoodCompositionAdapter(recipeList);
        RecipeRecyclerView.setAdapter(recipeAdapter);

    }
}
