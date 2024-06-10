package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.IItemRDA;
import com.nutriia.nutriia.interfaces.onItemClickListener;

import java.util.List;

public class ItemRDAAdapter extends RecyclerView.Adapter<ItemRDAAdapter.ItemRDAViewHolder> {

    private final FragmentManager fragmentManager;
    private final List<Fragment> fragments;

    public ItemRDAAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public ItemRDAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nostyle_item_fragment, parent, false)
                .findViewById(R.id.frame_layout);

        container.setId(View.generateViewId());
        return new ItemRDAViewHolder(container);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRDAViewHolder holder, int position) {
        Fragment fragment = fragments.get(position);
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        fragmentManager.beginTransaction()
                .add(holder.getContainer().getId(), fragment)
                .commit();
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ItemRDAViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout container;

        ItemRDAViewHolder(@NonNull FrameLayout container) {
            super(container);
            this.container = container;
        }

        public FrameLayout getContainer() {
            return container;
        }
    }
}