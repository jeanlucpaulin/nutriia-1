package com.nutriia.nutriiaemf.builders;

import android.content.res.Resources;

import com.nutriia.nutriiaemf.models.Goal;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoalsBuilder {
    private final Resources resources;
    private final String packageName;
    private final UserSharedPreferences userSharedPreferences;

    public GoalsBuilder(Resources resources, String packageName, UserSharedPreferences userSharedPreferences) {
        this.resources = resources;
        this.packageName = packageName;
        this.userSharedPreferences = userSharedPreferences;
    }
    public List<Goal> getGoals(Resources resources, String packageName) {
        return getGoals(resources, packageName, false);
    }

    public List<Goal> getGoals(Resources resources, String packageName, boolean excludeFirst) {
        List<Goal> goals = new ArrayList<>();
        List<String> icons = Arrays.asList(resources.getStringArray(R.array.icons_goals));
        List<String> goalsNames = Arrays.asList(resources.getStringArray(R.array.goals));
        List<String> goalsDescriptions = Arrays.asList(resources.getStringArray(R.array.goals_descriptions));

        for(int i = excludeFirst ? 1 : 0; i < icons.size(); i++) goals.add(new Goal(resources.getIdentifier(icons.get(i), "drawable", packageName), goalsNames.get(i), goalsDescriptions.get(i), userSharedPreferences.getGoal() == i));

        return goals;
    }

    public Goal getGoal(int goal) {
        List<Goal> goals = getGoals(resources, packageName);
        return goals.get(goal);
    }
}
