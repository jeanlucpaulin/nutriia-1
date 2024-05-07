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

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    private Menu menuItems;
    private OnItemClickListener listener;


    public DrawerItemAdapter(Menu menuItems) {
        this.menuItems = menuItems;
        this.setOnItemClickListener(new DrawerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.side_navigation_target) {
                    Log.d("Drawer", "Target clicked");
                } else if (item.getItemId() == R.id.side_navigation_sante) {
                    Log.d("Drawer", "Sante clicked");
                } else if (item.getItemId() == R.id.side_navigation_meet) {
                    Log.d("Drawer", "Meet clicked");
                } else if (item.getItemId() == R.id.side_navigation_follow_daily) {
                    Log.d("Drawer", "Follow Daily clicked");
                } else if (item.getItemId() == R.id.side_navigation_follow_monthly) {
                    Log.d("Drawer", "Follow Monthly clicked");
                } else if (item.getItemId() == R.id.side_navigation_forum) {
                    Log.d("Drawer", "Forum clicked");
                } else if (item.getItemId() == R.id.side_navigation_invite_friends) {
                    Log.d("Drawer", "Invite Friends clicked");
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuItem menuItem = menuItems.getItem(position);
        holder.title.setText(menuItem.getTitle());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(menuItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_item_text);
        }
    }
}