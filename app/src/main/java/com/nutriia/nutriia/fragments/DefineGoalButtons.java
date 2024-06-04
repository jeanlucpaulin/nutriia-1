package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.builders.GoalsBuilder;
import com.nutriia.nutriia.interfaces.OnClickOnGoal;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class DefineGoalButtons extends Fragment {
    private RecyclerView recyclerView;
    private Button validateButton;
    private OnNewGoalSelected callBack;
    private List<LinearLayout> layouts;
    private List<Goal> goals;

    public DefineGoalButtons() {
        super();
    }

    public DefineGoalButtons(OnNewGoalSelected callBack) {
        super();
        this.callBack = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_redefine_goal_main, container, false);

        layouts = new ArrayList<>();

        for(int id : new int[] {R.id.no_goal, R.id.weight_loss, R.id.muscle_gain, R.id.shape, R.id.sleep}) {
            layouts.add(view.findViewById(id));
        }

        layouts.forEach(layout -> layout.setOnClickListener(this::onClickOnGoal));

        GoalsBuilder goalsBuilder = new GoalsBuilder(getResources(), getActivity().getPackageName(), UserSharedPreferences.getInstance(getContext()));

        goals = goalsBuilder.getGoals(getResources(), getActivity().getPackageName());

        for(int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            LinearLayout layout = layouts.get(i);
            if(goal.isActual()) layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_text_meal, null));
            else layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_rounded, null));
        }


        return view;
    }

    private void onClickOnGoal(View view) {
        int selected = -1;
        for(int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            LinearLayout layout = layouts.get(i);
            if (layout.getId() == view.getId()) {
                layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_text_meal, null));
                if (!goal.isActual()) {
                    goal.setActual(true);
                    selected = i;
                }
            } else {
                if (goal.isActual()) goal.setActual(false);

                layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.linear_rounded, null));
            }
        }

        if(selected != -1) callBack.onNewGoalSelected(selected);
    }
}
