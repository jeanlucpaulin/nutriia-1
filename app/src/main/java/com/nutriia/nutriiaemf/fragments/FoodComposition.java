package com.nutriia.nutriiaemf.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FoodCompositionAdapter;
import com.nutriia.nutriiaemf.adapters.SuggestionsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodComposition extends AppFragment {

    private EditText editTextPlat;
    private ImageButton deleteButton;
    private TextView foodCalorie;
    private TextView foodName;
    private RelativeLayout titleAndCalorie;
    private LinearLayout macroMicroList;
    private RecyclerView macroNutrientsRecyclerView;
    private RecyclerView microNutrientsRecyclerView;
    private FoodCompositionAdapter macroNutrientAdapter;
    private FoodCompositionAdapter microNutrientAdapter;
    private List<String> macroNutrients;
    private List<String> microNutrients;
    private RecyclerView recyclerViewSuggestions;
    private SuggestionsAdapter suggestionsAdapter;
    private List<String> suggestions;
    private boolean isSuggestionSelected = false;
    private boolean isRequestInProgress = false;
    private Handler handler = new Handler();
    private Runnable runnable;

    private Context context;

    private View view;

    private View getView() {
        return view;
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
        view = inflater.inflate(R.layout.component_food_composition, frameLayout, false);

        editTextPlat = view.findViewById(R.id.editplat);
        deleteButton = view.findViewById(R.id.deleteButton);

        foodCalorie = view.findViewById(R.id.food_calorie);
        foodName = view.findViewById(R.id.food_name);

        macroNutrientsRecyclerView = view.findViewById(R.id.macronutrients);
        macroNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        macroNutrients = new ArrayList<>();
        macroNutrientAdapter = new FoodCompositionAdapter(macroNutrients);
        macroNutrientsRecyclerView.setAdapter(macroNutrientAdapter);

        microNutrientsRecyclerView = view.findViewById(R.id.micronutrients);
        microNutrientsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        microNutrients = new ArrayList<>();
        microNutrientAdapter = new FoodCompositionAdapter(microNutrients);
        microNutrientsRecyclerView.setAdapter(microNutrientAdapter);

        recyclerViewSuggestions = view.findViewById(R.id.recyclerViewSuggestions);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(context));
        suggestions = new ArrayList<>();
        suggestionsAdapter = new SuggestionsAdapter(suggestions, suggestion -> {
            if (!isRequestInProgress) {
                editTextPlat.setText(suggestion);
                recyclerViewSuggestions.setVisibility(View.GONE);
                isSuggestionSelected = true;
                fetchFoodComposition(suggestion);
            }
        });

        recyclerViewSuggestions.setAdapter(suggestionsAdapter);
        recyclerViewSuggestions.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        editTextPlat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isSuggestionSelected) {
                    isSuggestionSelected = false;
                } else {
                    if (charSequence.length() >= 2) {
                        if (charSequence.toString().trim().isEmpty()) {
                            suggestions.clear();
                            recyclerViewSuggestions.setVisibility(View.GONE);
                        } else {
                            handler.removeCallbacks(runnable);
                            runnable = () -> fetchSuggestions(charSequence.toString());
                            handler.postDelayed(runnable, 300);
                        }
                    } else {
                        suggestions.clear();
                        recyclerViewSuggestions.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    deleteButton.setEnabled(true);
                    deleteButton.setImageResource(R.drawable.menu_icon_delete_red);
                } else {
                    deleteButton.setEnabled(false);
                    deleteButton.setImageResource(R.drawable.menu_icon_delete_grey);
                }
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayoutComposition);
        layout.setOnClickListener(click -> {
            String foodName = editTextPlat.getText().toString().trim();
            if (!foodName.isEmpty()) {
                closeKeyboard();
                showCustomToast("Recherche de l'aliment en cours...", Toast.LENGTH_LONG);
                fetchFoodComposition(foodName);
            } else {
                showCustomToast("Veuillez saisir un nom d'aliment", Toast.LENGTH_SHORT);
            }
        });

        titleAndCalorie = view.findViewById(R.id.TitleandCalorie);
        macroMicroList = view.findViewById(R.id.macroMicroList);

        titleAndCalorie.setVisibility(View.GONE);
        macroMicroList.setVisibility(View.GONE);

        deleteButton.setOnClickListener(v -> {
            titleAndCalorie.setVisibility(View.GONE);
            macroMicroList.setVisibility(View.GONE);
            editTextPlat.setText("");
        });

        frameLayout.addView(view);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void fetchSuggestions(String query) {
        new Thread(() -> {
            try {
                if (query.trim().isEmpty()) {
                    ((Activity) context).runOnUiThread(() -> {
                        suggestions.clear();
                        recyclerViewSuggestions.setVisibility(View.GONE);
                    });
                    return;
                }

                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                URL url = new URL("https://nutriia.fr/api/ciqual/index.php?search=" + encodedQuery);
                Log.d("fetchSuggestions", "URL: " + url.toString());

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();
                Log.d("fetchSuggestions", "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d("fetchSuggestions", "Response JSON: " + jsonResponse.toString());

                    if (jsonResponse.has("suggestions")) {
                        JSONArray suggestionsArray = jsonResponse.getJSONArray("suggestions");
                        suggestions.clear();
                        String currentText = editTextPlat.getText().toString().trim().toLowerCase();

                        for (int i = 0; i < suggestionsArray.length(); i++) {
                            String suggestion = suggestionsArray.getString(i);
                            if (!suggestion.toLowerCase().equals(currentText)) {
                                suggestions.add(suggestion);
                            }
                        }

                        suggestions.remove(currentText);

                        ((Activity) context).runOnUiThread(() -> {
                            if (suggestions.isEmpty()) {
                                recyclerViewSuggestions.setVisibility(View.GONE);
                            } else {
                                if(!editTextPlat.getText().toString().trim().isEmpty()) {
                                    recyclerViewSuggestions.setVisibility(View.VISIBLE);
                                    suggestionsAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        Log.e("fetchSuggestions", "No value for suggestions in JSON response");
                    }
                } else {
                    Log.e("fetchSuggestions", "HTTP Error Code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("fetchSuggestions", "Exception: " + e.getMessage());
            }
        }).start();
    }

    private void fetchFoodComposition(String foodName) {
        if (isRequestInProgress) {
            Log.d("fetchFoodComposition", "Request already in progress, ignoring new request.");
            return;
        }

        isRequestInProgress = true;
        Log.d("fetchFoodComposition", "Starting request for: " + foodName);

        new Thread(() -> {
            try {
                String encodedFoodName = URLEncoder.encode(foodName, "UTF-8");
                URL url = new URL("https://nutriia.fr/api/ciqual/index.php?food=" + encodedFoodName);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();

                    String responseBody = response.toString();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        Log.d("fetchFoodComposition", "Response JSON: " + jsonResponse.toString());

                        if (jsonResponse.has("error")) {
                            String errorMessage = jsonResponse.getString("error");
                            ((Activity) context).runOnUiThread(() -> showCustomToast(errorMessage, Toast.LENGTH_SHORT));
                        } else {
                            getActivity().runOnUiThread(() -> {
                                try {
                                    updateUI(jsonResponse.getJSONObject("foodData"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        Log.e("fetchFoodComposition", "Invalid JSON response", e);
                        getActivity().runOnUiThread(() -> showCustomToast("Aucun résultat trouvé pour " + foodName, Toast.LENGTH_SHORT));
                    }
                } else {
                    getActivity().runOnUiThread(() -> showCustomToast("Erreur de connexion", Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> showCustomToast("Une erreur est survenue", Toast.LENGTH_SHORT));
            } finally {
                isRequestInProgress = false;
                Log.d("fetchFoodComposition", "Request completed for: " + foodName);
            }
        }).start();
    }

    private void updateUI(JSONObject foodData) {
        RelativeLayout titleAndCalorie = getView().findViewById(R.id.TitleandCalorie);
        LinearLayout macroMicroList = getView().findViewById(R.id.macroMicroList);

        try {
            if (foodData.has("calories") && foodData.has("food_name") &&
                    (foodData.has("macro_nutrients") || foodData.has("micro_nutrients"))) {

                titleAndCalorie.setVisibility(View.VISIBLE);
                macroMicroList.setVisibility(View.VISIBLE);

                String calories = foodData.getString("calories") + " Kcal / 100g";
                if (foodCalorie != null) {
                    foodCalorie.setText(calories);
                }

                String foodNameStr = foodData.getString("food_name");
                if (foodName != null) {
                    foodName.setText(foodNameStr);
                }

                if (foodData.has("macro_nutrients")) {
                    JSONObject macroNutrientsObj = foodData.getJSONObject("macro_nutrients");
                    macroNutrients.clear();
                    for (Iterator<String> it = macroNutrientsObj.keys(); it.hasNext(); ) {
                        String key = it.next();
                        String nutrient = key + " (" + macroNutrientsObj.getString(key) + ")";
                        macroNutrients.add(nutrient);
                    }
                    // Sort the macroNutrients
                    macroNutrients.sort((s1, s2) -> customNutrientComparator(s1.split(" ")[0], s2.split(" ")[0]));
                    if (macroNutrientAdapter != null) {
                        macroNutrientAdapter.notifyDataSetChanged();
                    }
                }

                if (foodData.has("micro_nutrients")) {
                    JSONObject microNutrientsObj = foodData.getJSONObject("micro_nutrients");
                    microNutrients.clear();
                    for (Iterator<String> it = microNutrientsObj.keys(); it.hasNext(); ) {
                        String key = it.next();
                        String nutrient = key + " (" + microNutrientsObj.getString(key) + ")";
                        microNutrients.add(nutrient);
                    }
                    // Sort the microNutrients
                    microNutrients.sort((s1, s2) -> customNutrientComparator(s1.split(" ")[0], s2.split(" ")[0]));
                    if (microNutrientAdapter != null) {
                        microNutrientAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                titleAndCalorie.setVisibility(View.GONE);
                macroMicroList.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int customNutrientComparator(String s1, String s2) {
        if (s1.startsWith("Vitamin B") && s2.startsWith("Vitamin B")) {
            int bNumber1 = Integer.parseInt(s1.substring(9));
            int bNumber2 = Integer.parseInt(s2.substring(9));
            return Integer.compare(bNumber1, bNumber2);
        } else {
            return s1.compareTo(s2);
        }
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
