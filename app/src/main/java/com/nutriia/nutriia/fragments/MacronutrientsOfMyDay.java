package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nutriia.nutriia.builders.DayBuilder;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.interfaces.APIResponseValidateDay;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MacronutrientsOfMyDay extends Fragment implements APIResponseValidateDay, APIResponseRDA {

    private RecyclerView recyclerView;
    private final List<Nutrient> nutrientsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_my_day, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        TextView textView = view.findViewById(R.id.component_title);
        textView.setText(R.string.macronutrients_of_my_day);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        APISend.obtainsNewGoalRDA(this.getActivity(), this);

        APISend.addValidateDayListener(this);

        return view;
    }

    @Override
    public void onValidateDayResponse(Day day) {
        update(day);
    }

    @Override
    public void onAPIRDAResponse() {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getContext());

        Day day = new DayBuilder().buildOnlyWithGoal(userSharedPreferences);

        update(day);
    }

    private void update(Day day) {
        nutrientsList.clear();
        nutrientsList.addAll(day.getMacroNutrients().values());
        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(getContext(), nutrientsList);
        recyclerView.setAdapter(dayProgressionAdapter);
    }
}