package com.nutriia.nutriia.fragments;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.RenderEffect;
import android.graphics.Shader;
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
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.interfaces.OnUserProfileChanged;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendedDailyAmount extends Fragment implements APIResponseRDA, OnNewGoalSelected, OnUserProfileChanged {

    private static final int MAX_DISPLAYED_ITEMS = 10;
    private static boolean DISPLAY_ALL_ITEMS = false;

    private final List<Fragment> macronutrients = new ArrayList<>();
    private final List<Fragment> micronutrients = new ArrayList<>();
    private TextView caloriesText;
    private TextView fibersText;
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
        fibersText = view.findViewById(R.id.fibers_amount);

        macronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        micronutrientsListView.setLayoutManager(new LinearLayoutManager(getContext()));

        APISend.obtainsNewGoalRDA(this.getActivity(), this);

        arrow = view.findViewById(R.id.arrowDetails);
        detailsText = view.findViewById(R.id.textDetails);
        LinearLayout reverseDISPLAY_ALL_ITEMS = view.findViewById(R.id.detailsButton);
        reverseDISPLAY_ALL_ITEMS.setOnClickListener(v -> reverseDisplayedItems());

        String text = getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);

        arrow.setRotation(DISPLAY_ALL_ITEMS ? 270 : 90);

        return view;
    }

    @Override
    public void onAPIRDAResponse() {
        removeBlurEffect();
        Day day = new DayBuilder().buildOnlyWithGoal(UserSharedPreferences.getInstance(getContext()));
        List<Nutrient> macroNutrients = new ArrayList<>(day.getMacroNutrients().values());
        List<Nutrient> microNutrients = new ArrayList<>(day.getMicroNutrients().values());

        // Sort the nutrients
        Comparator<Nutrient> nutrientComparator = new Comparator<Nutrient>() {
            @Override
            public int compare(Nutrient n1, Nutrient n2) {
                String name1 = n1.getName();
                String name2 = n2.getName();

                String nonDigitPart1 = name1.replaceAll("\\d", "");
                String nonDigitPart2 = name2.replaceAll("\\d", "");

                int nonDigitPartComparison = nonDigitPart1.compareTo(nonDigitPart2);
                if (nonDigitPartComparison != 0) {
                    return nonDigitPartComparison;
                } else {
                    String digitPart1 = name1.replaceAll("\\D", "");
                    String digitPart2 = name2.replaceAll("\\D", "");

                    // If there are no digits in the name, compare the names directly
                    if (digitPart1.isEmpty() && digitPart2.isEmpty()) {
                        return name1.compareTo(name2);
                    }

                    // If one name has digits and the other doesn't, the one with digits comes first
                    if (digitPart1.isEmpty()) {
                        return 1;
                    }
                    if (digitPart2.isEmpty()) {
                        return -1;
                    }

                    // If both names have digits, compare the digit parts as integers
                    return Integer.compare(Integer.parseInt(digitPart1), Integer.parseInt(digitPart2));
                }
            }
        };
        Collections.sort(macroNutrients, nutrientComparator);
        Collections.sort(microNutrients, nutrientComparator);

        // Convert nutrients to fragments
        List<Fragment> macroFragments = new ArrayList<>();
        for(Nutrient nutrient : macroNutrients) {
            macroFragments.add(new NutrientAJR(nutrient));
        }
        List<Fragment> microFragments = new ArrayList<>();
        for(Nutrient nutrient : microNutrients) {
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
        fibersText.setText(String.valueOf(day.getFiber().getValue()));
    }

    private void reverseDisplayedItems() {
        DISPLAY_ALL_ITEMS = !DISPLAY_ALL_ITEMS;
        onAPIRDAResponse();
        arrow.setRotation(arrow.getRotation() + 180);
        String text = getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        scrollView.fullScroll(View.FOCUS_UP);
    }

    @Override
    public void onNewGoalSelected(int position) {
        addBlurEffect();
    }

    private void addBlurEffect() {
        float radius = 20f;
        RenderEffect renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP);
        view.setRenderEffect(renderEffect);
    }

    private void removeBlurEffect() {
        view.setRenderEffect(null);
    }

    @Override
    public void onUserProfileChanged() {
        addBlurEffect();
    }
}
