package com.nutriia.nutriia.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DayProgressionAdapter;

import java.util.ArrayList;
import java.util.List;

public class MicronutrientsOfMyDay extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_my_day, container, false);

        Resources resources = getResources();

        ListView listView = view.findViewById(R.id.listView);
        TextView textView = view.findViewById(R.id.component_title);
        textView.setText(R.string.micronutrients_of_my_day);


        List<Nutrient> nutrientsList = new ArrayList<>();
        for(String micronutrient : resources.getStringArray(R.array.list_micronutrients)) {
            nutrientsList.add(new Nutrient(micronutrient, 100, resources.getString(R.string.grams_unit), 50));
        }


        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(getContext(), nutrientsList);
        listView.setAdapter(dayProgressionAdapter);

        return view;

    }
}
