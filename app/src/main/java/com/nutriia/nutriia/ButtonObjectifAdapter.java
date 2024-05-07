package com.nutriia.nutriia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;

import java.util.List;

public class ButtonObjectifAdapter extends RecyclerView.Adapter<ButtonObjectifAdapter.ViewHolder> {
    private List<ButtonObjectif> buttonDataList;

    public ButtonObjectifAdapter(List<ButtonObjectif> buttonDataList) {
        this.buttonDataList = buttonDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objectif_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ButtonObjectif buttonData = buttonDataList.get(position);
        holder.button.setCompoundDrawablesWithIntrinsicBounds(buttonData.getImageResId(), 0, 0, 0);
        holder.button.setText(buttonData.getText());
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