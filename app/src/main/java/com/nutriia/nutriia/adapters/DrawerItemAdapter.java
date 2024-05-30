package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.HealthInformationActivity;
import com.nutriia.nutriia.activities.ObjectifActivity;
import com.nutriia.nutriia.activities.RedefineGoalActivity;
import com.nutriia.nutriia.activities.WebViewActivity;
import com.nutriia.nutriia.fragments.DefineMyGoal;
import com.nutriia.nutriia.fragments.RedefineMyGoal;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;

public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    private Menu menuItems;
    private OnItemClickListener listener;

    private Context context;

    private onActivityFinishListener activityFinishListener;

    private AppCompatActivity activity;


    public DrawerItemAdapter(Menu menuItems, AppCompatActivity activity, onActivityFinishListener activityFinishListener) {
        this.menuItems = menuItems;
        this.context = activity.getApplicationContext();
        this.activityFinishListener = activityFinishListener;
        ActivityResultLauncher<Intent> activityLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (activityFinishListener != null) activityFinishListener.onActivityFinish();
                }
        );
        this.setOnItemClickListener(new DrawerItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.side_navigation_target) {
                    activityLauncher.launch(new Intent(context, RedefineGoalActivity.class));
                } else if (item.getItemId() == R.id.side_navigation_sante) {
                    activityLauncher.launch(new Intent(context, HealthInformationActivity.class));
                /*} else if (item.getItemId() == R.id.side_navigation_meet) {
                    Log.d("Drawer", "Meet clicked");
                } else if (item.getItemId() == R.id.side_navigation_follow_daily) {
                    Log.d("Drawer", "Follow Daily clicked");
                } else if (item.getItemId() == R.id.side_navigation_follow_monthly) {
                    Log.d("Drawer", "Follow Monthly clicked");*/
                } else if (item.getItemId() == R.id.side_navigation_forum) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", "https://nutriia.fr/fr/Forum/");
                    intent.putExtra("title", "Forum");
                    activityLauncher.launch(intent);
                }else if (item.getItemId() == R.id.side_navigation_invite_friends) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share_app_message));
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    activity.startActivity(shareIntent);
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