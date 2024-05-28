package com.nutriia.nutriia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.ItemRDA;
import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.ItemRDAAdapter;
import com.nutriia.nutriia.builders.DayBuilder;
import com.nutriia.nutriia.interfaces.APIResponseRDA;
import com.nutriia.nutriia.interfaces.IItemRDA;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RecommendedDailyAmount extends Fragment implements APIResponseRDA {

    private static final int MAX_DISPLAYED_ITEMS = 10;
    private static boolean DISPLAY_ALL_ITEMS = false;

    private final List<Fragment> macronutrients = new ArrayList<>();
    private final List<Fragment> micronutrients = new ArrayList<>();
    private TextView caloriesText;
    private RecyclerView macronutrientsListView;
    private RecyclerView micronutrientsListView;
    private ImageView arrow;
    private TextView detailsText;
    private View view;

    private AppCompatActivity activity;

    public RecommendedDailyAmount(AppCompatActivity activity) {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.component_recommanded_daily_amount, container, false);

        macronutrientsListView = view.findViewById(R.id.macronutrients_list);
        micronutrientsListView = view.findViewById(R.id.micronutrients_list);
        caloriesText = view.findViewById(R.id.energy_amount);

        macronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        micronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));

        APISend.obtainsNewGoalRDA(this.getActivity(), this);

        arrow = view.findViewById(R.id.arrowDetails);
        detailsText = view.findViewById(R.id.textDetails);
        LinearLayout reverseDisplayAllItems = view.findViewById(R.id.detailsButton);
        reverseDisplayAllItems.setOnClickListener(v -> reverseDisplayAllItems());

        return view;
    }

    @Override
    public void onAPIRDAResponse() {
        Day day = new DayBuilder().buildOnlyWithGoal(UserSharedPreferences.getInstance(getContext()));
        List<Fragment> macroFragments = new ArrayList<>();
        List<Fragment> microFragments = new ArrayList<>();

        for(Nutrient nutrient : day.getMacroNutrients().values()) {
            macroFragments.add(new NutrientAJR(nutrient));
        }
        for(Nutrient nutrient : day.getMicroNutrients().values()) {
            microFragments.add(new NutrientAJR(nutrient));
        }

        macronutrients.clear();
        macronutrients.addAll(macroFragments);
        micronutrients.clear();
        micronutrients.addAll(microFragments);

        while (!DISPLAY_ALL_ITEMS && microFragments.size() + macroFragments.size() > MAX_DISPLAYED_ITEMS) {
            if(!microFragments.isEmpty()) microFragments.remove(microFragments.size() - 1);
            else if (!macroFragments.isEmpty()) macroFragments.remove(macroFragments.size() - 1);
            else break;
        }

        ItemRDAAdapter macroAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), macroFragments);
        ItemRDAAdapter microAdapter = new ItemRDAAdapter(activity.getSupportFragmentManager(), microFragments);


        macronutrientsListView.setAdapter(macroAdapter);
        micronutrientsListView.setAdapter(microAdapter);

        caloriesText.setText(String.valueOf(day.getCalorie().getValue()));
    }

    private void reverseDisplayAllItems() {
        DISPLAY_ALL_ITEMS = !DISPLAY_ALL_ITEMS;
        onAPIRDAResponse();
        arrow.setRotation(arrow.getRotation() + 180);
        String text = getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_UP);
    }
}
