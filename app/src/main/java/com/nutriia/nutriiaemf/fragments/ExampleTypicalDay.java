package com.nutriia.nutriiaemf.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriia.nutriiaemf.models.Dish;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.models.TypicalDay;
import com.nutriia.nutriiaemf.activities.DayAnalysisActivity;
import com.nutriia.nutriiaemf.interfaces.APIResponseRDA;
import com.nutriia.nutriiaemf.network.APISend;
import com.nutriia.nutriiaemf.resources.Translator;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ExampleTypicalDay extends AppFragment implements APIResponseRDA {

    private View breakfastView;
    private View lunchView;
    private View dinnerView;
    private View snackView;
    private View view;
    private Button relaunchButton = null;

    private Context context;

    private Context getContext() {
        return context;
    }

    private Activity getActivity() {
        return (Activity) context;
    }


    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.componenent_exemple_typical_day, frameLayout, false);

        breakfastView = view.findViewById(R.id.breakfast);
        lunchView = view.findViewById(R.id.lunch);
        dinnerView = view.findViewById(R.id.dinner);
        snackView = view.findViewById(R.id.snack);

        setDishes(breakfastView, new TypicalDay("Petit déjeuner", new ArrayList<>()));
        setDishes(lunchView, new TypicalDay("Déjeuner", new ArrayList<>()));
        setDishes(dinnerView, new TypicalDay("Diner", new ArrayList<>()));
        setDishes(snackView, new TypicalDay("Collations", new ArrayList<>()));

        // Get the typical day from the API
        APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this);

        relaunchButton = view.findViewById(R.id.relaunchButton);
        relaunchButton.setOnClickListener(v -> {
            relaunchButton.setEnabled(false);
            showCustomToast("Génération d'une journée type en cours...", Toast.LENGTH_SHORT);
            UserSharedPreferences.getInstance(getContext()).clearTypicalDay();
            APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this);
        });

        LinearLayout layout = view.findViewById(R.id.linearLayoutMeal);
        layout.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), DayAnalysisActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            getActivity().startActivity(intent);
        });

        frameLayout.addView(view);
    }

    /**
     * Update the typical day when new Recommended Daily Amounts for the user are available
     */
    @Override
    public void onAPIRDAResponse() {
        APISend.obtainsTypicalDay(getActivity(), this::setTypicalDy, this);
    }

    private void setTypicalDy(List<TypicalDay> dishes) {
        if(relaunchButton != null) relaunchButton.setEnabled(true);
        for (TypicalDay typicalDay : dishes) {
            switch (typicalDay.getName()) {
                case "breakfast":
                    setDishes(breakfastView, typicalDay);
                    break;
                case "lunch":
                    setDishes(lunchView, typicalDay);
                    break;
                case "dinner":
                    setDishes(dinnerView, typicalDay);
                    break;
                case "snack":
                    setDishes(snackView, typicalDay);
                    break;
            }
        }
    }

    private void setDishes(View view, TypicalDay typicalDay) {
        TextView textView = view.findViewById(R.id.textMenuType);
        TextView editText = view.findViewById(R.id.textDish);
        textView.setText(Translator.translate(typicalDay.getName()));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typicalDay.getDishes().size(); i++) {
            Dish dish = typicalDay.getDishes().get(i);
            stringBuilder.append(dish.getName()).append(i == typicalDay.getDishes().size()-1 ? "" : "\n");
        }
        editText.setText(stringBuilder.toString());
        removeLoading();
    }

    private void showCustomToast(String message, int duration) {
        getActivity().runOnUiThread(() -> {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, getActivity().findViewById(R.id.toast_layout_root));

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            Toast toast = new Toast(getContext());
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        });
        setLoading();
    }

    /**
     * Set the loading effect on the view
     */
    private void setLoading(){
        float radius = 20f;
        RenderEffect renderEffect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP);
            view.findViewById(R.id.linearLayout).setRenderEffect(renderEffect);
        }
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.relaunchButton).setBackgroundColor(getContext().getColor(R.color.grey));
    }

    /**
     * Remove the loading effect on the view
     */
    private void removeLoading(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            view.findViewById(R.id.linearLayout).setRenderEffect(null);
        }
        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        view.findViewById(R.id.relaunchButton).setBackgroundColor(getContext().getColor(R.color.orange));
    }
}