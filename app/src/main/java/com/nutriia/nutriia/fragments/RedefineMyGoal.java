package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.Goal;

public class RedefineMyGoal extends Fragment {
    private Goal goal;
    private View view;
    private LinearLayout goalLayout;

    public RedefineMyGoal(Goal goal) {
        this.goal = goal;
    }

    public Goal getGoal() {
        return goal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.component_goals, container, false);
        goalLayout = view.findViewById(R.id.goalLayout);
        if(goal.isActual()) {
            goalLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_text_meal, null));
        }
        else {
            goalLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_rounded, null));
        }

        TextView textView = view.findViewById(R.id.goal);
        ImageView imageView = view.findViewById(R.id.icon);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), goal.getImageResId(), null));
        textView.setText(goal.getText());

        return view;
    }

    public void setGoalSelected(boolean selected) {
        if(selected == goal.isActual()) return;
        goal.setActual(selected);
        if(selected) {
            goalLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_text_meal, null));
        }
        else {
            goalLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_rounded, null));
        }
    }
}
