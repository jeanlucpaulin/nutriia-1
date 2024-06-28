package com.nutriia.nutriiaemf.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriiaemf.models.Goal;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.builders.GoalsBuilder;
import com.nutriia.nutriiaemf.interfaces.OnNewGoalSelected;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class DefineGoalButtons extends AppFragment {
    private RecyclerView recyclerView;
    private Button validateButton;
    private OnNewGoalSelected callBack;
    private List<LinearLayout> layouts;
    private List<Goal> goals;

    private Context context;

    public DefineGoalButtons(OnNewGoalSelected callBack) {
        super();
        this.callBack = callBack;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.component_redefine_goal_main, frameLayout, false);

        layouts = new ArrayList<>();

        for(int id : new int[] {R.id.no_goal, R.id.weight_loss, R.id.muscle_gain, R.id.shape, R.id.sleep}) {
            layouts.add(view.findViewById(id));
        }

        layouts.forEach(layout -> layout.setOnClickListener(this::onClickOnGoal));

        GoalsBuilder goalsBuilder = new GoalsBuilder(context.getResources(), context.getPackageName(), UserSharedPreferences.getInstance(context));

        goals = goalsBuilder.getGoals(context.getResources(), context.getPackageName());

        for(int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            LinearLayout layout = layouts.get(i);
            if(goal.isActual()) layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_text_meal, null));
            else layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_buttons_click, null));
        }

        frameLayout.addView(view);
    }

    private void onClickOnGoal(View view) {
        int selected = -1;
        for(int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            LinearLayout layout = layouts.get(i);
            //set the background of the selected goal
            if (layout.getId() == view.getId()) {
                layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_text_meal, null));
                //check to not set the goal to actual if it is already actual
                if (!goal.isActual()) {
                    goal.setActual(true);
                    selected = i;
                }
            } else {
                if (goal.isActual()) goal.setActual(false);

                //set the background of the other goals
                layout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rounded_buttons_click, null));
            }
        }

        //call the callback
        if(selected != -1) callBack.onNewGoalSelected(selected);
    }
}
