package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;

import java.util.Objects;

public class RedefineMyGoal extends Fragment {
    private Goal goal;

    public RedefineMyGoal(Goal goal) {
        this.goal = goal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(goal.isActual() ? R.layout.component_goals_actual : R.layout.component_goals, container, false);
        TextView textView = view.findViewById(R.id.goal);
        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier(goal.getIcon(), "drawable", requireContext().getPackageName()), null));
        textView.setText(goal.getName());
        return view;
    }
}
