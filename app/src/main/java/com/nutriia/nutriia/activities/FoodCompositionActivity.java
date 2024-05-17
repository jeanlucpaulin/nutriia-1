package com.nutriia.nutriia.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FoodCompositionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

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

        String foodNameStr = getIntent().getStringExtra("food_name");
        fetchFoodComposition(foodNameStr);
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchFoodComposition(String foodName) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String foodName = strings[0];
                try {
                    String encodedFoodName = URLEncoder.encode(foodName, "UTF-8");
                    URL url = new URL("https://nutriia.fr/api/ciqual/index.php?food=" + encodedFoodName);

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    try {
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }
                            bufferedReader.close();
                            return stringBuilder.toString();
                        } else {
                            Log.e("HTTP_ERROR", "Server returned: " + responseCode);
                            return null;
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        foodCalorie.setText(jsonResponse.getString("calories"));
                        FoodCompositionActivity.this.foodName.setText(jsonResponse.getString("food_name"));

                        JSONArray macroArray = jsonResponse.getJSONArray("macro_nutrients");
                        for (int i = 0; i < macroArray.length(); i++) {
                            macroNutrients.add(macroArray.getString(i));
                        }
                        macroNutrientAdapter.notifyDataSetChanged();

                        JSONArray microArray = jsonResponse.getJSONArray("micro_nutrients");
                        for (int i = 0; i < microArray.length(); i++) {
                            microNutrients.add(microArray.getString(i));
                        }
                        microNutrientAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("HTTP_ERROR", "Failed to fetch food composition");
                }
            }

        }.execute(foodName);
    }
}
