package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.HealthInformationActivity;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.Objects;

public class MorePrecision extends Fragment {

    private AppCompatActivity activity;
    private onActivityFinishListener activityFinishListener;

    public MorePrecision(AppCompatActivity activity, onActivityFinishListener activityFinishListener) {
        this.activity = activity;
        this.activityFinishListener = activityFinishListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_more_precision, container, false);


        Button defineMyProfileButton = view.findViewById(R.id.define_profile);

        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (activityFinishListener != null) activityFinishListener.onActivityFinish();
                }
        );
        TextView userWeight = view.findViewById(R.id.weight_data);
        TextView userHeight = view.findViewById(R.id.height_data);
        updateFields(userWeight, userHeight, UserSharedPreferences.getInstance(activity.getApplicationContext()));

        defineMyProfileButton.setOnClickListener(v -> activityLauncher.launch(new Intent(getActivity(), HealthInformationActivity.class)));

        return view;
    }

    private void updateFields(TextView userWeight, TextView userHeight, UserSharedPreferences userSharedPreferences)
    {
        if(userSharedPreferences.isUserWeightDefined()) {
            String userWeightText = userSharedPreferences.getWeight() + " kg";
            userWeight.setText(userWeightText);
        }

        if(userSharedPreferences.isUserHeightDefined()) {
            String userHeightText = userSharedPreferences.getHeight() + " cm";
            userHeight.setText(userHeightText);
        }
    }
}
