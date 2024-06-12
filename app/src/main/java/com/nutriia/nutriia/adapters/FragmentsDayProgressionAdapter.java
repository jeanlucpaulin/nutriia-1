package com.nutriia.nutriia.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.fragments.AppFragment;
import com.nutriia.nutriia.resources.Translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FragmentsDayProgressionAdapter {
    private List<Nutrient> nutrients;

    private Context context;

    private LinearLayout linearLayout;

    public FragmentsDayProgressionAdapter(Context context, LinearLayout linearLayout) {
        this.nutrients = new ArrayList<>();
        this.context = context;
        this.linearLayout = linearLayout;
    }

    public void addAll(List<Nutrient> nutrients) {
        for (Nutrient nutrient : nutrients) {
            add(nutrient);
        }
    }

    public void add(Nutrient nutrient) {
        nutrients.add(nutrient);

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_my_day, frameLayout, false);

        TextView nameTextView = view.findViewById(R.id.item_name);
        TextView valueTextView = view.findViewById(R.id.item_value);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        ImageButton infoButton = view.findViewById(R.id.info_button);
        ImageButton alertButton = view.findViewById(R.id.alert_button);

        String nutrientName = Translator.translate(nutrient.getName()) + " (" + nutrient.getProgress() + " " + nutrient.getUnit() + ")";
        nameTextView.setText(nutrientName);
        valueTextView.setText(String.valueOf(nutrient.getValue() + " " + nutrient.getUnit()));
        int progressRatio = (int) ((float) nutrient.getProgress() / nutrient.getValue() * 100);

        if(progressRatio <= 30) progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        else if (progressRatio <= 70) progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellow)));
        else progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lime)));
        progressBar.setProgress(progressRatio);
        if (progressRatio >= 200) {
            alertButton.setVisibility(View.VISIBLE);
        } else {
            alertButton.setVisibility(View.GONE);
        }
        infoButton.setOnClickListener(v -> {
            String nutrientInfo = Nutrient.getNutrientInfo(context, nutrient.getName());
            showCustomDialog(nutrientInfo);
        });
        alertButton.setOnClickListener(v -> {
            int warningMessageId = getWarningMessageId(nutrient.getName());
            String warningMessage = context.getString(warningMessageId);
            showCustomDialog(warningMessage);
        });

        frameLayout.addView(view);

        linearLayout.addView(frameLayout);
    }

    private void showCustomDialog(String message) {
        ((Activity) context).runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.RoundedAlertDialog);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, null);

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            Button btnOk = layout.findViewById(R.id.btn_ok);
            btnOk.setVisibility(View.VISIBLE);  // Make the button visible

            builder.setView(layout);
            AlertDialog dialog = builder.create();

            btnOk.setOnClickListener(v -> {
                dialog.dismiss();  // Dismiss the dialog when the button is clicked
            });

            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  // Adjusting the dialog width to match the content width
        });
    }

    private int getWarningMessageId(String nutrientName) {
        HashMap<String, Integer> warningMessagesMap = new HashMap<>();
        warningMessagesMap.put("vitamin_a", R.string.warning_vitamin_a);
        warningMessagesMap.put("vitamin_c", R.string.warning_vitamin_c);
        warningMessagesMap.put("vitamin_d", R.string.warning_vitamin_d);
        warningMessagesMap.put("vitamin_e", R.string.warning_vitamin_e);
        warningMessagesMap.put("vitamin_k", R.string.warning_vitamin_k);
        warningMessagesMap.put("vitamin_b1", R.string.warning_vitamin_b1);
        warningMessagesMap.put("vitamin_b2", R.string.warning_vitamin_b2);
        warningMessagesMap.put("vitamin_b3", R.string.warning_vitamin_b3);
        warningMessagesMap.put("vitamin_b6", R.string.warning_vitamin_b6);
        warningMessagesMap.put("vitamin_b9", R.string.warning_vitamin_b9);
        warningMessagesMap.put("vitamin_b12", R.string.warning_vitamin_b12);
        warningMessagesMap.put("vitamin_b7", R.string.warning_vitamin_b7);
        warningMessagesMap.put("vitamin_b5", R.string.warning_vitamin_b5);
        warningMessagesMap.put("calcium", R.string.warning_calcium);
        warningMessagesMap.put("copper", R.string.warning_copper);
        warningMessagesMap.put("iron", R.string.warning_iron);
        warningMessagesMap.put("magnesium", R.string.warning_magnesium);
        warningMessagesMap.put("manganese", R.string.warning_manganese);
        warningMessagesMap.put("phosphorus", R.string.warning_phosphorus);
        warningMessagesMap.put("potassium", R.string.warning_potassium);
        warningMessagesMap.put("zinc", R.string.warning_zinc);
        warningMessagesMap.put("carbohydrates", R.string.warning_carbohydrates);
        warningMessagesMap.put("proteins", R.string.warning_proteins);
        warningMessagesMap.put("lipids", R.string.warning_lipids);
        warningMessagesMap.put("fibers", R.string.warning_fibers);
        Integer warningMessageId = warningMessagesMap.get(nutrientName);
        if (warningMessageId == null) {
            warningMessageId = R.string.default_warning_message;
        }
        return warningMessageId;
    }
}
