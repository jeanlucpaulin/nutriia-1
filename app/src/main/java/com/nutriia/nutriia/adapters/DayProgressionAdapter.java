package com.nutriia.nutriia.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.resources.Translator;

import java.util.List;

public class DayProgressionAdapter extends RecyclerView.Adapter<DayProgressionAdapter.ViewHolder> {
    private Context context;
    private List<Nutrient> nutrientsList;

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

        holder.infoButton.setOnClickListener(v -> {
            String nutrientInfo = Nutrient.getNutrientInfo(context, nutrient.getName());
            showCustomDialog(nutrientInfo);
        });
    }

    @Override
    public int getItemCount() {
        return nutrientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView value;
        ProgressBar progressBar;
        ImageButton infoButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            value = itemView.findViewById(R.id.item_value);
            progressBar = itemView.findViewById(R.id.progressBar);
            infoButton = itemView.findViewById(R.id.info_button);
        }
    }

    private void showCustomDialog(String message) {
        ((Activity) context).runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.RoundedAlertDialog);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, null);

            TextView textView = layout.findViewById(R.id.toast_text);
            textView.setText(message);

            ImageView imageView = layout.findViewById(R.id.toast_image);

            Button btnOk = layout.findViewById(R.id.btn_ok);
            btnOk.setVisibility(View.VISIBLE);  // Make the button visible

            builder.setView(layout);
            AlertDialog dialog = builder.create();

            btnOk.setOnClickListener(v -> {
                dialog.dismiss();  // Dismiss the dialog when the button is clicked
            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  // Adjusting the dialog width to match the content width
        });
    }
}