package com.nutriia.nutriia.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.OnClickOnGoal;
import com.nutriia.nutriia.interfaces.onItemClickListener;

import java.util.List;

public class ButtonObjectifAdapter extends RecyclerView.Adapter<ButtonObjectifAdapter.ViewHolder> implements onItemClickListener {
    private final List<Goal> buttonGoals;

    private OnClickOnGoal listener;

    public ButtonObjectifAdapter(List<Goal> buttonGoals, OnClickOnGoal listener) {
        this.buttonGoals = buttonGoals;
        this.listener = listener;

    }

    @Override
    public void onItemClick(int position) {
        if(buttonGoals.get(position).isSelected()) {
            buttonGoals.get(position).setSelected(false);
            listener.onClickOnGoal(false);
            notifyDataSetChanged();
            return;
        }
        for (Goal buttonGoal : buttonGoals) buttonGoal.setSelected(false);

        buttonGoals.get(position).setSelected(true);
        listener.onClickOnGoal(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.objectif_button, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goal buttonData = buttonGoals.get(position);
        Drawable img = ContextCompat.getDrawable(holder.button.getContext(), buttonData.getImageResId());
        if (img != null) {
            img.setBounds(0, 0, (int)(img.getIntrinsicWidth()*0.5),
                    (int)(img.getIntrinsicHeight()*0.5));
            holder.button.setCompoundDrawables(img, null, null, null);
        }
        holder.button.setText(buttonData.getText());
        holder.button.setSelected(buttonData.isSelected()); // Make sure the selected state of the button is correctly updated

        if (buttonData.isSelected()) {
            holder.button.setAlpha(0.5f); // 50% transparent
        } else {
            holder.button.setAlpha(1.0f); // Fully opaque
        }
    }

    @Override
    public int getItemCount() {
        return buttonGoals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button;

        public ViewHolder(View view, onItemClickListener listener) {
            super(view);
            button = view.findViewById(R.id.customButton);
            button.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition());
            });
        }
    }
}