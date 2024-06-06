package com.nutriia.nutriia.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.resources.Translator;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DayProgressionAdapter extends RecyclerView.Adapter<DayProgressionAdapter.ViewHolder> {
    private final Context context;
    private final List<Nutrient> nutrientsList;

    public DayProgressionAdapter(Context context, List<Nutrient> nutrientsList) {
        this.context = context;
        this.nutrientsList = nutrientsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nutrient nutrient = nutrientsList.get(position);
        String name = Translator.translate(nutrient.getName()) + " (" + nutrient.getProgress() + " " + nutrient.getUnit() + ")";
        holder.name.setText(name);
        holder.value.setText(String.valueOf(nutrient.getValue() + " " + nutrient.getUnit()));
        int progressRatio = (int) ((float) nutrient.getProgress() / nutrient.getValue() * 100);

        if(progressRatio <= 30) holder.progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        else if (progressRatio <= 70) holder.progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellow)));
        else holder.progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lime)));
        holder.progressBar.setProgress(progressRatio);
        if (progressRatio >= 200) {
            holder.alertButton.setVisibility(View.VISIBLE);
        } else {
            holder.alertButton.setVisibility(View.GONE);
        }
        holder.infoButton.setOnClickListener(v -> {
            String nutrientInfo = Nutrient.getNutrientInfo(context, nutrient.getName());
            showCustomDialog(nutrientInfo);
        });
        holder.alertButton.setOnClickListener(v -> {
            int warningMessageId = getWarningMessageId(nutrient.getName());
            String warningMessage = context.getString(warningMessageId);
            showCustomDialog(warningMessage);
        });
    }

    @Override
    public int getItemCount() {
        return nutrientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView value;
        ProgressBar progressBar;
        ImageButton infoButton;
        ImageButton alertButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            value = itemView.findViewById(R.id.item_value);
            progressBar = itemView.findViewById(R.id.progressBar);
            infoButton = itemView.findViewById(R.id.info_button);
            alertButton = itemView.findViewById(R.id.alert_button);
        }
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