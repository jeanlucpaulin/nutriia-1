package com.nutriia.nutriia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.nutriia.nutriia.R;

import java.util.List;

public class FragmentsAdapter extends RecyclerView.Adapter<FragmentsAdapter.FragmentViewHolder> {
    private final FragmentManager fragmentManager;
    private final List<Fragment> fragments;

    public FragmentsAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
    }

    public static class FragmentViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout container;

        public FragmentViewHolder(@NonNull FrameLayout container) {
            super(container);
            this.container = container;
        }

        public FrameLayout getContainer() {
            return container;
        }
    }

    @NonNull
    @Override
    public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout container = new FrameLayout(parent.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        container.setId(View.generateViewId());
        return new FragmentViewHolder(container);
    }


    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position) {
        Fragment fragment = fragments.get(position);
        fragmentManager.beginTransaction()
                .replace(holder.getContainer().getId(), fragment)
                .commit();
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}