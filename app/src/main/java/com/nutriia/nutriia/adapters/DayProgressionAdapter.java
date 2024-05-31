package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    }

    @Override
    public int getItemCount() {
        return nutrientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView value;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            value = itemView.findViewById(R.id.item_value);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}