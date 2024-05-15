package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.IItemRDA;

import java.util.List;

public class ItemRDAAdapter extends RecyclerView.Adapter<ItemRDAAdapter.ItemRDAViewHolder> {

    private Context context;
    private List<IItemRDA> items;

    public ItemRDAAdapter(Context context, List<IItemRDA> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemRDAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment, parent, false)
                .findViewById(R.id.frame_layout);

        container.setId(View.generateViewId());
        return new ItemRDAViewHolder(container);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRDAViewHolder holder, int position) {
        IItemRDA item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemAmount.setText(String.valueOf(item.getAmount()));
        holder.itemUnit.setText(item.getUnit());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemRDAViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemAmount;
        TextView itemUnit;

        ItemRDAViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemAmount = itemView.findViewById(R.id.amount);
            itemUnit = itemView.findViewById(R.id.unit);
        }
    }
}