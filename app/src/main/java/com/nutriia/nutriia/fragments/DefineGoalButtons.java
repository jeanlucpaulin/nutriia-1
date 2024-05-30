package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = view.findViewById(R.id.recyclerViewRedefineGoal);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        setGoals();

        return view;
    }

    private void setGoals() {
        List<Fragment> fragments = new ArrayList<>();

        GoalsBuilder goalsBuilder = new GoalsBuilder(getResources(), getActivity().getPackageName(), UserSharedPreferences.getInstance(getContext()));

        List<Goal> goalsList = goalsBuilder.getGoals(getResources(), getActivity().getPackageName());

        goalsList.forEach(goal -> {
            fragments.add(new RedefineMyGoal(goal));
        });

        FragmentsAdapter adapter = new FragmentsAdapter(getChildFragmentManager(), fragments, true, callBack);
        recyclerView.setAdapter(adapter);
    }

}
