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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DayProgressionAdapter;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.interfaces.APIResponseValidateDay;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MicronutrientsOfMyDay extends Fragment implements APIResponseValidateDay, APIResponseRDA {

    private RecyclerView recyclerView;
    private final List<Nutrient> nutrientsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_my_day, container, false);

        Resources resources = getResources();

        recyclerView = view.findViewById(R.id.recyclerView);
        TextView textView = view.findViewById(R.id.component_title);
        textView.setText(R.string.micronutrients_of_my_day);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Nutrient> nutrientsList = new ArrayList<>();
        for(String micronutrient : resources.getStringArray(R.array.list_micronutrients)) {
            nutrientsList.add(new Nutrient(micronutrient, 100, resources.getString(R.string.grams_unit), 0));
        }

        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(getContext(), nutrientsList);
        recyclerView.setAdapter(dayProgressionAdapter);

        APISend.addValidateDayListener(this);

        return view;

    }

    @Override
    public void onValidateDayResponse(Day day) {
        nutrientsList.clear();
        nutrientsList.addAll(day.getMicroNutrients().values());
        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(getContext(), nutrientsList);
        recyclerView.setAdapter(dayProgressionAdapter);
    }

    @Override
    public void onAPIRDAResponse() {

    }


}
