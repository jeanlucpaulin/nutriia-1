package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.HealthInformationActivity;
import com.nutriia.nutriia.user.UserSharedPreferences;

public class MorePrecision extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_more_precision, container, false);


        Button defineMyProfileButton = view.findViewById(R.id.define_profile);
        TextView userWeight = view.findViewById(R.id.weight_data);
        TextView userHeight = view.findViewById(R.id.height_data);
        TextView userBodyMassIndex = view.findViewById(R.id.body_mass_index_data);

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getContext());

        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> updateFields(userWeight, userHeight, userBodyMassIndex, userSharedPreferences)
        );

        updateFields(userWeight, userHeight, userBodyMassIndex, userSharedPreferences);

        defineMyProfileButton.setOnClickListener(v -> activityLauncher.launch(new Intent(getActivity(), HealthInformationActivity.class)));

        return view;
    }

    private void updateFields(TextView userWeight, TextView userHeight, TextView userBodyMassIndex, UserSharedPreferences userSharedPreferences)
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
            String userBodyMassIndexText = String.valueOf((int) Math.round(userBodyMassIndexValue));
            userBodyMassIndex.setText(userBodyMassIndexText);
        }
    }
}
