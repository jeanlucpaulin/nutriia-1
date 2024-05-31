package com.nutriia.nutriia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;

import java.util.List;

public class FoodCompositionAdapter extends RecyclerView.Adapter<FoodCompositionAdapter.MacroNutrientViewHolder> {

    private List<String> macroNutrientList;

    public FoodCompositionAdapter(List<String> macroNutrientList) {
        this.macroNutrientList = macroNutrientList;
    }

    @NonNull
    @Override
    public MacroNutrientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.macro_nutrient_item, parent, false);
        return new MacroNutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MacroNutrientViewHolder holder, int position) {
        String macroNutrient = macroNutrientList.get(position);
        holder.macroNutrientTextView.setText(macroNutrient);
    }

    @Override
    public int getItemCount() {
        return macroNutrientList.size();
    }

    public void updateData(List<String> newData) {
        this.macroNutrientList.clear();
        this.macroNutrientList.addAll(newData);
        notifyDataSetChanged();
    }

    public static class MacroNutrientViewHolder extends RecyclerView.ViewHolder {
        TextView macroNutrientTextView;

        public MacroNutrientViewHolder(@NonNull View itemView) {
            super(itemView);
            macroNutrientTextView = itemView.findViewById(R.id.macro_nutrient_text);
        }
    }
}
