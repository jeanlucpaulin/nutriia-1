package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.MealsAdapter;
import com.nutriia.nutriia.network.APISend;
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
            textView.setText(meals.get(i).getName());

        }


        Button validateButton = view.findViewById(R.id.validateButton);

        validateButton.setOnClickListener(v -> {
            Context context = getContext();
            Map<String, Set<String>> userInput = new HashMap<>();

            for(int viewId : viewIds) {
                TextView textView = view.findViewById(viewId).findViewById(R.id.textView);
                EditText editText = view.findViewById(viewId).findViewById(R.id.editText);

                Set<String> inputs = new HashSet<>();

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
            }

            APISend.sendValidateDay(getActivity(), userInput);
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
}
