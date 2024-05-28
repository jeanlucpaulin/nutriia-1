package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.FoodCompositionActivity;
import com.nutriia.nutriia.adapters.SuggestionsAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FoodComposition extends Fragment {

    private EditText editTextPlat;
    private RecyclerView recyclerViewSuggestions;
    private SuggestionsAdapter suggestionsAdapter;
    private List<String> suggestions;
    private boolean isSuggestionSelected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_food_composition, container, false);

        editTextPlat = view.findViewById(R.id.editplat);
        recyclerViewSuggestions = view.findViewById(R.id.recyclerViewSuggestions);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestions = new ArrayList<>();
        suggestionsAdapter = new SuggestionsAdapter(suggestions, suggestion -> {
            editTextPlat.setText(suggestion);
            recyclerViewSuggestions.setVisibility(View.GONE);
            isSuggestionSelected = true;
            fetchFoodComposition(suggestion);
        });

        recyclerViewSuggestions.setAdapter(suggestionsAdapter);

        editTextPlat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isSuggestionSelected) {
                    isSuggestionSelected = false;
                } else {
                    if (charSequence.toString().trim().isEmpty()) {
                        suggestions.clear();
                        recyclerViewSuggestions.setVisibility(View.GONE);
                    } else {
                        fetchSuggestions(charSequence.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    suggestions.clear();
                    recyclerViewSuggestions.setVisibility(View.GONE);
                }
            }
        });

        LinearLayout layout = view.findViewById(R.id.linearLayoutComposition);
        layout.setOnClickListener(click -> {
            String foodName = editTextPlat.getText().toString().trim();
            if (!foodName.isEmpty()) {
                showCustomToast("Recherche de l'aliment en cours...", Toast.LENGTH_LONG);
                fetchFoodComposition(foodName);
            } else {
                showCustomToast("Veuillez saisir un nom d'aliment", Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

    private void fetchSuggestions(String query) {
        new Thread(() -> {
            try {
                if (query.trim().isEmpty()) {
                    getActivity().runOnUiThread(() -> {
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

                        // Filtrer les suggestions
                        for (int i = 0; i < suggestionsArray.length(); i++) {
                            String suggestion = suggestionsArray.getString(i);
                            if (!suggestion.toLowerCase().equals(currentText)) {
                                suggestions.add(suggestion);
                            }
                        }

                        // Supprimer l'aliment s'il est déjà présent dans la liste
                        if (suggestions.contains(currentText)) {
                            suggestions.remove(currentText);
                        }

                        getActivity().runOnUiThread(() -> {
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

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Log.d("FoodComposition", "Response JSON: " + jsonResponse.toString());

                    if (jsonResponse.has("error")) {
                        String errorMessage = jsonResponse.getString("error");
                        getActivity().runOnUiThread(() -> showCustomToast(errorMessage, Toast.LENGTH_SHORT));
                    } else {
                        getActivity().runOnUiThread(() -> {
                            launchFoodCompositionActivity(foodName, jsonResponse);
                        });
                    }
                } else {
                    getActivity().runOnUiThread(() -> showCustomToast("Erreur de connexion", Toast.LENGTH_SHORT));
                }
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> showCustomToast("Une erreur est survenue", Toast.LENGTH_SHORT));
            }
        }).start();
    }

    private void launchFoodCompositionActivity(String foodName, JSONObject foodData) {
        Intent intent = new Intent(getContext(), FoodCompositionActivity.class);
        intent.putExtra("food_name", foodName);
        intent.putExtra("food_data", foodData.toString());
        startActivity(intent);
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
