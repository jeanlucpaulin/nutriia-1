package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;
import android.widget.ProgressBar;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodCompositionActivity extends AppCompatActivity {

    private RecyclerView macroNutrientsRecyclerView;
    private RecyclerView microNutrientsRecyclerView;
    private FoodCompositionAdapter macroNutrientAdapter;
    private FoodCompositionAdapter microNutrientAdapter;
    private List<String> macroNutrients;
    private List<String> microNutrients;
    private TextView foodCalorie;
    private TextView foodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_composition);

        TextView headerBackTitle = findViewById(R.id.title);
        headerBackTitle.setText("Composition \nd'un aliment");

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(click -> finish());

        foodCalorie = findViewById(R.id.food_calorie);
        foodName = findViewById(R.id.food_name);

        macroNutrientsRecyclerView = findViewById(R.id.macronutrients);
        macroNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        macroNutrients = new ArrayList<>();
        macroNutrientAdapter = new FoodCompositionAdapter(macroNutrients);
        macroNutrientsRecyclerView.setAdapter(macroNutrientAdapter);

        microNutrientsRecyclerView = findViewById(R.id.micronutrients);
        microNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        microNutrients = new ArrayList<>();
        microNutrientAdapter = new FoodCompositionAdapter(microNutrients);
        microNutrientsRecyclerView.setAdapter(microNutrientAdapter);

        String foodDataStr = getIntent().getStringExtra("food_data");
        Log.d("FoodCompositionActivity", "Received food data: " + foodDataStr); // Log received data

        try {
            JSONObject foodData = new JSONObject(foodDataStr);
            updateUI(foodData.getJSONObject("foodData")); // Access the nested foodData object
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FoodCompositionActivity", "Failed to parse food data");
        }
    }

    private void updateUI(JSONObject foodData) {
        try {
            Log.d("FoodCompositionActivity", "Updating UI with food data: " + foodData.toString()); // Log update process

            if (foodData.has("calories")) {
                foodCalorie.setText(foodData.getString("calories") + " Kcal / 100g");
            } else {
                foodCalorie.setText("N/A");
            }

            if (foodData.has("food_name")) {
                foodName.setText(foodData.getString("food_name"));
            } else {
                foodName.setText("N/A");
            }

            if (foodData.has("macro_nutrients")) {
                JSONObject macroNutrientsObj = foodData.getJSONObject("macro_nutrients");
                macroNutrients.clear(); // Clear the existing data
                for (Iterator<String> it = macroNutrientsObj.keys(); it.hasNext(); ) {
                    String key = it.next();
                    macroNutrients.add(key + " (" + macroNutrientsObj.getString(key) + ") ");
                }
                macroNutrientAdapter.notifyDataSetChanged();
            }

            if (foodData.has("micro_nutrients")) {
                JSONObject microNutrientsObj = foodData.getJSONObject("micro_nutrients");
                microNutrients.clear(); // Clear the existing data
                for (Iterator<String> it = microNutrientsObj.keys(); it.hasNext(); ) {
                    String key = it.next();
                    microNutrients.add(key + " (" + microNutrientsObj.getString(key) + ") ");
                }
                microNutrientAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FoodCompositionActivity", "Error updating UI with food data", e);
        }
    }
}
