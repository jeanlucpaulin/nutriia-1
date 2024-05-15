package com.nutriia.nutriia.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;

import java.util.List;

public class ButtonObjectifAdapter extends RecyclerView.Adapter<ButtonObjectifAdapter.ViewHolder> {
    private List<Goal> buttonDataList;

    public ButtonObjectifAdapter(List<Goal> buttonDataList) {
        this.buttonDataList = buttonDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objectif_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goal buttonData = buttonDataList.get(position);
        Drawable img = ContextCompat.getDrawable(holder.button.getContext(), buttonData.getImageResId());
        if (img != null) {
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth()*0.5),
                    (int)(img.getIntrinsicHeight()*0.5));
            holder.button.setCompoundDrawables(img, null, null, null);
        }
        holder.button.setText(buttonData.getText());
        holder.button.setSelected(buttonData.isSelected()); // Make sure the selected state of the button is correctly updated
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deselect all other buttons
                for (Goal otherButtonData : buttonDataList) {
                    if (otherButtonData != buttonData) {
                        otherButtonData.setSelected(false);
                    }
                }
                // Change the selected state of the button
                buttonData.setSelected(!buttonData.isSelected());
                notifyDataSetChanged(); // Update the adapter to reflect the changes
            }
        });

        if (buttonData.isSelected()) {
            holder.button.setAlpha(0.5f); // 50% transparent
        } else {
            holder.button.setAlpha(1.0f); // Fully opaque
        }
    }

    @Override
    public int getItemCount() {
        return buttonDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button;

        public ViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.customButton);
        }
    }
}