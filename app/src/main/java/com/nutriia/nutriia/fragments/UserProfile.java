package com.nutriia.nutriia.fragments;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.builders.GoalsBuilder;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;
import com.nutriia.nutriia.user.UserSharedPreferences;

public class UserProfile extends Fragment implements OnNewGoalSelected {

    private AppCompatActivity activity;
    private onActivityFinishListener activityFinishListener;
    ImageView userGoalIcon;
    TextView userGoal;

    public UserProfile(AppCompatActivity activity, onActivityFinishListener activityFinishListener) {
        this.activity = activity;
        this.activityFinishListener = activityFinishListener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_informations, container, false);

        TextView userWeight = view.findViewById(R.id.weight_data);
        TextView userHeight = view.findViewById(R.id.height_data);
        TextView userBodyMassIndex = view.findViewById(R.id.body_mass_index_data);
        TextView userBodyMassIndexUnit = view.findViewById(R.id.body_mass_index_unit);
        userGoalIcon = view.findViewById(R.id.image_objective);
        userGoal = view.findViewById(R.id.title_objective);
        LinearLayout imcContainer = view.findViewById(R.id.imc_container);
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(activity.getApplicationContext());

        updateFields(userWeight, userHeight, userBodyMassIndex, userBodyMassIndexUnit, imcContainer, userGoalIcon, userGoal, sharedPreferences);

        return view;
    }

    private void updateFields(TextView userWeight, TextView userHeight, TextView userBodyMassIndex, TextView userBodyMassIndexUnit, LinearLayout imcContainer, ImageView goalIcon, TextView goalName, UserSharedPreferences userSharedPreferences)
    {
        if(userSharedPreferences.isUserWeightDefined()) {
            String userWeightText = userSharedPreferences.getWeight() + " kg";
            userWeight.setText(userWeightText);
        }

        if(userSharedPreferences.isUserHeightDefined()) {
            String userHeightText = userSharedPreferences.getHeight() + " cm";
            userHeight.setText(userHeightText);
        }

        if(userSharedPreferences.isUserWeightDefined() && userSharedPreferences.isUserHeightDefined()) {
            double userHeightInMeters = userSharedPreferences.getHeight() / 100.0;
            double userBodyMassIndexValue = userSharedPreferences.getWeight() / (userHeightInMeters * userHeightInMeters);
            String userBodyMassIndexUnitText = String.valueOf((int) Math.round(userBodyMassIndexValue));

            String userBodyMassIndexText;
            int backgroundColor = R.color.lime;
            String[] imcValues = getResources().getStringArray(R.array.imc_indicator);
            if (userBodyMassIndexValue < 16.5) {
                userBodyMassIndexText = imcValues[0];
                backgroundColor = R.color.IMC1;
            } else if (userBodyMassIndexValue < 18.5) {
                userBodyMassIndexText = imcValues[1];
                backgroundColor = R.color.IMC2;
            } else if (userBodyMassIndexValue < 25) {
                userBodyMassIndexText = imcValues[2];
                backgroundColor = R.color.IMC3;
            } else if (userBodyMassIndexValue < 30) {
                userBodyMassIndexText = imcValues[3];
                backgroundColor = R.color.IMC4;
            } else if (userBodyMassIndexValue < 35) {
                userBodyMassIndexText = imcValues[4];
                backgroundColor = R.color.IMC5;
            } else if (userBodyMassIndexValue < 40) {
                userBodyMassIndexText = imcValues[5];
                backgroundColor = R.color.IMC6;
            } else {
                userBodyMassIndexText = imcValues[6];
                backgroundColor = R.color.IMC7;
            }

            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), backgroundColor));
            imcContainer.setBackgroundTintList(colorStateList);
            userBodyMassIndexUnit.setText(userBodyMassIndexUnitText);
            userBodyMassIndex.setText(userBodyMassIndexText);
        }

        updateGoal(userSharedPreferences.getGoal());
    }

    @Override
    public void onNewGoalSelected(int position) {
        updateGoal(position);
    }

    private void updateGoal(int goalIndex) {
        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(requireContext());
        GoalsBuilder goalsBuilder = new GoalsBuilder(getResources(), requireContext().getPackageName(), userSharedPreferences);
        Goal goal = goalsBuilder.getGoal(goalIndex);
        userGoalIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), goal.getImageResId(), null));
        userGoal.setText(goal.getText());
    }
}
