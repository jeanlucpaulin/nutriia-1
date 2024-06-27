package com.nutriia.nutriiaemf.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nutriia.nutriiaemf.models.Day;
import com.nutriia.nutriiaemf.models.Nutrient;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FragmentsDayProgressionAdapter;
import com.nutriia.nutriiaemf.builders.DayBuilder;
import com.nutriia.nutriiaemf.interfaces.APIResponseRDA;
import com.nutriia.nutriiaemf.interfaces.OnValidateDay;
import com.nutriia.nutriiaemf.network.APISend;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NutrientsOfMyDay extends AppFragment implements OnValidateDay, APIResponseRDA {
    private static boolean DISPLAY_ALL_ITEMS = false;
    private LinearLayout layout_fragments;
    private final List<Nutrient> nutrientsList = new ArrayList<>();
    private View view;
    private TextView detailsText;
    private ImageView arrow;
    private Day day;
    private Context context;

    private final Type type;

    public enum Type {
        ENERGY,
        MACRONUTRIENTS,
        MICRONUTRIENTS
    }

    public NutrientsOfMyDay(Type type) {
        this.type = type;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.component_my_day, frameLayout, false);

        layout_fragments = view.findViewById(R.id.linear_layout_fragment);
        TextView textView = view.findViewById(R.id.component_title);

        if(type == Type.MACRONUTRIENTS) textView.setText(R.string.macronutrients_of_my_day);
        else if(type == Type.MICRONUTRIENTS) textView.setText(R.string.micronutrients_of_my_day);
        else if(type == Type.ENERGY) textView.setVisibility(View.GONE);

        APISend.obtainsNewGoalRDA((Activity) context, this);

        LinearLayout detailsLayout = view.findViewById(R.id.details_layout);
        detailsText = view.findViewById(R.id.textDetails);
        arrow = view.findViewById(R.id.arrowDetails);

        detailsLayout.setOnClickListener(v -> reverseDisplayItems());

        String text = context.getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);

        arrow.setRotation(DISPLAY_ALL_ITEMS ? 270 : 90);

        frameLayout.addView(view);
    }

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        setLoading();
    }

    private void setLoading(){
        float radius = 20f;
        RenderEffect renderEffect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP);
            view.findViewById(R.id.linear_layout_fragment).setRenderEffect(renderEffect);
        }
        view.findViewById(R.id.progressBarLoading).setVisibility(View.VISIBLE);

        if (view.findViewById(R.id.details_layout).getVisibility() == View.VISIBLE) {
            view.findViewById(R.id.details_layout).setVisibility(View.GONE);
        }
    }

    private void removeLoadingEffect(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            view.findViewById(R.id.linear_layout_fragment).setRenderEffect(null);
        }

        view.findViewById(R.id.progressBarLoading).setVisibility(View.GONE);

        if (view.findViewById(R.id.details_layout).getVisibility() == View.GONE) {
            view.findViewById(R.id.details_layout).setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onValidateDayResponse(Day day) {
        this.day = day;
        update();
        removeLoadingEffect();
    }

    @Override
    public void onAPIRDAResponse() {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(context);
        this.day = new DayBuilder().buildOnlyWithGoal(userSharedPreferences);
        update();
        removeLoadingEffect();
    }

    private void update() {
        if(day == null) {
            Log.w("NutrientsOfMyDay", "Day is null");
            return;
        }
        nutrientsList.clear();
        layout_fragments.removeAllViews();
        List<Nutrient> nutrients;
        if(type == Type.MACRONUTRIENTS) nutrients = new ArrayList<>(day.getMacroNutrients().values());
        else if(type == Type.MICRONUTRIENTS) nutrients = new ArrayList<>(day.getMicroNutrients().values());
        else {
            nutrients = new ArrayList<>();
            nutrients.add(day.getCalorie());
            nutrients.add(day.getFiber());
        }
        if(DISPLAY_ALL_ITEMS) nutrientsList.addAll(nutrients);
        else {
            for(int i = 0; i < Settings.getMaxDisplayedItems() && i < nutrients.size(); i++) {
                nutrientsList.add(nutrients.get(i));
            }
        }
        FragmentsDayProgressionAdapter adapter = new FragmentsDayProgressionAdapter(context, layout_fragments);

        adapter.addAll(nutrientsList);
        if(nutrientsList.size() < Settings.getMaxDisplayedItems()) {
            LinearLayout layout = view.findViewById(R.id.details_layout);
            layout.setVisibility(View.INVISIBLE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
        }
        else view.findViewById(R.id.details_layout).setVisibility(View.VISIBLE);
    }

    private void reverseDisplayItems() {
        DISPLAY_ALL_ITEMS = !DISPLAY_ALL_ITEMS;
        String text = context.getResources().getString(DISPLAY_ALL_ITEMS ? R.string.less_details : R.string.more_details);
        detailsText.setText(text);
        arrow.setRotation(DISPLAY_ALL_ITEMS ? 270 : 90);
        update();
    }
}