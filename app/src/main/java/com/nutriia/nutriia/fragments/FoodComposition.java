package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.FoodCompositionActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FoodComposition extends Fragment {

    private EditText editTextPlat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_food_composition, container, false);

        editTextPlat = view.findViewById(R.id.editplat);

        LinearLayout layout = view.findViewById(R.id.linearLayoutComposition);
        layout.setOnClickListener(click -> {
            String foodName = editTextPlat.getText().toString().trim();
            if (!foodName.isEmpty()) {
                checkFoodExists(foodName);
            } else {
                showCustomToast("Veuillez saisir un nom d'aliment");
            }
        });

        return view;
    }

    private void checkFoodExists(String foodName) {
        new Thread(() -> {
            try {
                String encodedFoodName = URLEncoder.encode(foodName, "UTF-8");
                URL url = new URL("https://nutriia.fr/api/ciqual/check_food.php?food=" + encodedFoodName);

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
                    if (jsonResponse.has("error")) {
                        String errorMessage = jsonResponse.getString("error");
                        if (errorMessage.equals("Aucun aliment correspondant trouvé")) {
                            getActivity().runOnUiThread(() -> showCustomToast("Aucun aliment correspondant trouvé"));
                        } else {
                            getActivity().runOnUiThread(() -> showCustomToast(errorMessage));
                        }
                    } else {
                        launchFoodCompositionActivity(foodName);
                    }
                } else {
                    getActivity().runOnUiThread(() -> showCustomToast("Erreur de connexion"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> showCustomToast("Une erreur est survenue"));
            }
        }).start();
    }

    private void launchFoodCompositionActivity(String foodName) {
        Intent intent = new Intent(getContext(), FoodCompositionActivity.class);
        intent.putExtra("food_name", foodName);
        startActivity(intent);
    }

    private void showCustomToast(String message) {
        getActivity().runOnUiThread(() -> {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, getActivity().findViewById(R.id.toast_layout_root));

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            Toast toast = new Toast(getContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        });
    }
}
