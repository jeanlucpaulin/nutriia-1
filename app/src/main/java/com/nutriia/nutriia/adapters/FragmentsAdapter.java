package com.nutriia.nutriia.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.fragments.FormationBanner;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.fragments.RedefineMyGoal;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.interfaces.onItemClickListener;

import java.util.Map;

public class FragmentsAdapter extends RecyclerView.Adapter<FragmentsAdapter.FragmentViewHolder> implements onItemClickListener {
    private final FragmentManager fragmentManager;
    private final Map<Integer, Fragment> fragments;
    private final boolean allowClick;
    private final OnNewGoalSelected callBack;

    public FragmentsAdapter(FragmentManager fragmentManager, Map<Integer, Fragment> fragments) {
        this(fragmentManager, fragments, false, null);
    }

    public FragmentsAdapter(FragmentManager fragmentManager, Map<Integer, Fragment> fragments, boolean allowClick, OnNewGoalSelected callBack) {
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
        this.allowClick = allowClick;
        this.callBack = callBack;
    }

    @Override
    public void onItemClick(int position) {
        if(!allowClick) return;
        if(((RedefineMyGoal) fragments.get(position)).getGoal().isActual()) return;
        for(Fragment fragment : fragments.values()) {
            if(fragment instanceof RedefineMyGoal) {
                ((RedefineMyGoal) fragment).setGoalSelected(false);
            }
        }
        ((RedefineMyGoal) fragments.get(position)).setGoalSelected(true);

        callBack.onNewGoalSelected(position);
    }

    public static class FragmentViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout container;

        public FragmentViewHolder(@NonNull FrameLayout container, @Nullable onItemClickListener listener) {
            super(container);
            this.container = container;
            container.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        public FrameLayout getContainer() {
            return container;
        }
    }

    @NonNull
    @Override
    public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment, parent, false)
                .findViewById(R.id.frame_layout);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(40, 20, 40, 20);

        container.setLayoutParams(layoutParams);
        container.setId(View.generateViewId());
        return new FragmentViewHolder(container, this);
    }


    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position) {
        Fragment fragment = fragments.get(position);
        Log.d("FragmentsAdapter", "onBindViewHolder: " + fragment.getClass().getSimpleName() + " " + position);
        if (fragment.isAdded()) {
            return;
        } else {
            fragmentManager.beginTransaction().add(holder.getContainer().getId(), fragment).commit();
        }


        /*if (fragment instanceof PageTitle) holder.getContainer().setBackground(null);


        if (fragment instanceof FormationBanner) {
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(0, -20, 0, 0);
            holder.getContainer().setLayoutParams(layoutParams);
            holder.getContainer().setBackground(null);
        }*/
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
