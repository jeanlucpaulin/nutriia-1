package com.nutriia.nutriia.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;

public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.ViewHolder> {

    private Menu menuItems;

    public DrawerItemAdapter(Menu menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = menuItems.getItem(position);
        holder.title.setText(menuItem.getTitle());
        Log.d("DrawerItemAdapter", "Binding item at position " + position);
    }

    @Override
    public int getItemCount() {
        Log.d("DrawerItemAdapter", "Item number " + menuItems.size());
        return menuItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_item_text);
        }
    }
}