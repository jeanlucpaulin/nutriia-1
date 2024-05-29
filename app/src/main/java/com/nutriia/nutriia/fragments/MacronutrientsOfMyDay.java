package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nutriia.nutriia.resources.Settings;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MacronutrientsOfMyDay extends Fragment implements APIResponseValidateDay, APIResponseRDA {
    private static boolean DISPLAY_ALL_ITEMS = false;
    private RecyclerView recyclerView;
    private final List<Nutrient> nutrientsList = new ArrayList<>();
    private View view;
    private TextView detailsText;
    private ImageView arrow;
    private Day day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.component_my_day, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        TextView textView = view.findViewById(R.id.component_title);
        textView.setText(R.string.macronutrients_of_my_day);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        APISend.obtainsNewGoalRDA(this.getActivity(), this);

        APISend.addValidateDayListener(this);

        LinearLayout detailsLayout = view.findViewById(R.id.details_layout);
        detailsText = view.findViewById(R.id.textDetails);
        arrow = view.findViewById(R.id.arrowDetails);

        detailsLayout.setOnClickListener(v -> reverseDisplayItems());

        String text = getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);

        arrow.setRotation(DISPLAY_ALL_ITEMS ? 270 : 90);

        return view;
    }

    @Override
    public void onValidateDayResponse(Day day) {
        this.day = day;
        update();
    }

    @Override
    public void onAPIRDAResponse() {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getContext());

        this.day = new DayBuilder().buildOnlyWithGoal(userSharedPreferences);
        update();
    }

    private void update() {
        nutrientsList.clear();
        List<Nutrient> macronutrients = new ArrayList<>(day.getMacroNutrients().values());
        if(DISPLAY_ALL_ITEMS) nutrientsList.addAll(macronutrients);
        else {
            for(int i = 0; i < Settings.getMaxDisplayedItems() && i < macronutrients.size(); i++) {
                nutrientsList.add(macronutrients.get(i));
            }
        }
        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(getContext(), nutrientsList);
        recyclerView.setAdapter(dayProgressionAdapter);
        if(nutrientsList.size() < Settings.getMaxDisplayedItems()) view.findViewById(R.id.details_layout).setVisibility(View.INVISIBLE);
    }

    private void reverseDisplayItems() {
        DISPLAY_ALL_ITEMS = !DISPLAY_ALL_ITEMS;
        String text = getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);
        arrow.setRotation(DISPLAY_ALL_ITEMS ? 270 : 90);
        update();
    }
}