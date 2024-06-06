package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.MealsAdapter;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.resources.Translator;
import com.nutriia.nutriia.user.Saver;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MyRealDay extends Fragment {

    List<Meal> meals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.componenent_my_real_day, container, false);

        meals = new ArrayList<>(Arrays.asList(new Meal("Breakfast"), new Meal("Lunch"), new Meal("Dinner"), new Meal("Snack")));

        int[] viewIds = new int[] {R.id.breakfast, R.id.lunch, R.id.dinner, R.id.snack};

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getContext());

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        String date = userSharedPreferences.getMRDDate();

        if(!currentDate.equals(date) && !date.isEmpty()) userSharedPreferences.clearMRD();

        for(int i = 0; i < viewIds.length; i++) {
            TextView textView = view.findViewById(viewIds[i]).findViewById(R.id.textView);
            EditText editText = view.findViewById(viewIds[i]).findViewById(R.id.editText);

            StringBuilder content = new StringBuilder();

            List<String> dishes = getDishes(viewIds[i]);

            for (int j = 0; j < dishes.size(); j++) {
                content.append(dishes.get(j)).append(j == dishes.size()-1 ? "" : "\n");
            }

            editText.setText(content.toString());
            textView.setText(Translator.translate(meals.get(i).getName()));

        }

        TextView textViewCalories = view.findViewById(R.id.calories);
        int calories = userSharedPreferences.getMRDCalories();
        textViewCalories.setText(String.valueOf(Math.max(calories, 0) + " kcal"));


        Button validateButton = view.findViewById(R.id.validateButton);
        for(int i = 0; i < viewIds.length; i++) {

            EditText editText = view.findViewById(viewIds[i]).findViewById(R.id.editText);
            ImageButton deleteButton = view.findViewById(viewIds[i]).findViewById(R.id.imageButton);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        deleteButton.setImageResource(R.drawable.baseline_close_30_red);
                        deleteButton.setEnabled(true);
                    } else {
                        deleteButton.setEnabled(false);
                        deleteButton.setImageResource(R.drawable.baseline_close_30);
                    }
                }
            });

            deleteButton.setOnClickListener(v -> {
                editText.setText("");
            });
        }

        validateButton.setOnClickListener(v -> {
            showCustomToast("Comparaison de votre journée en cours...", Toast.LENGTH_LONG);
            validateButton.setEnabled(false);
            Context context = getContext();
            Map<String, Set<String>> userInput = new HashMap<>();
            boolean send = false;

            for(int viewId : viewIds) {
                TextView textView = view.findViewById(viewId).findViewById(R.id.textView);
                EditText editText = view.findViewById(viewId).findViewById(R.id.editText); // Define EditText here

                Set<String> inputs = new HashSet<>();
                List<String> dishes = getDishes(viewId);

                if(viewId == R.id.breakfast) {
                    inputs = Saver.saveMRDInputBreakfast(context, editText.getText().toString());
                }
                else if(viewId == R.id.lunch) {
                    inputs = Saver.saveMRDInputLunch(context, editText.getText().toString());
                }
                else if(viewId == R.id.dinner) {
                    inputs = Saver.saveMRDInputDinner(context, editText.getText().toString());
                }
                else if(viewId == R.id.snack){
                    inputs = Saver.saveMRDInputSnack(context, editText.getText().toString());
                }

                userInput.put(textView.getText().toString().toLowerCase(), inputs);

                if(!send && !inputEquals(dishes, new ArrayList<>(inputs))) send = true;
            }

            if(send) {
                APISend.sendValidateDay(getActivity(), userInput, result -> {
                    validateButton.setEnabled(true);
                    TextView caloriesTextView = view.findViewById(R.id.calories);
                    int caloriesMRD = userSharedPreferences.getMRDCalories();
                    caloriesTextView.setText(String.valueOf((Math.max(caloriesMRD, 0)) + " kcal"));
                });
            }
            else {
                Log.d("MyRealDay", "No changes to send");
                validateButton.setEnabled(true);
            }
        });

        return view;
    }

    private List<String> getDishes(int id) {
        List<String> dishes = new ArrayList<>();
        if(id == R.id.breakfast) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(getContext()).getMRDABreakfast());
        }
        else if(id == R.id.lunch) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(getContext()).getMRDALunch());
        }
        else if(id == R.id.dinner) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(getContext()).getMRDADinner());
        }
        else if(id == R.id.snack) {
            dishes = new ArrayList<>(UserSharedPreferences.getInstance(getContext()).getMRDASnack());
        }
        return dishes;
    }

    private boolean inputEquals(List<String> list1, List<String> list2) {
        if(list1.size() != list2.size()) return false;
        for(int i = 0; i < list1.size(); i++) {
            if(!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

    private void showCustomToast(String message, int duration) {
        requireActivity().runOnUiThread(() -> {
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
