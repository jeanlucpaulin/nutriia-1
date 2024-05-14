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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.HealthInformationActivity;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.Objects;

public class MorePrecision extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_more_precision, container, false);


        Button defineMyProfileButton = view.findViewById(R.id.define_profile);
        TextView userWeight = view.findViewById(R.id.weight_data);
        TextView userHeight = view.findViewById(R.id.height_data);
        TextView userBodyMassIndex = view.findViewById(R.id.body_mass_index_data);
        TextView userBodyMassIndexUnit = view.findViewById(R.id.body_mass_index_unit);
        LinearLayout imcContainer = view.findViewById(R.id.imc_container);

        UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getContext());

        ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> updateFields(userWeight, userHeight, userBodyMassIndex, userBodyMassIndexUnit, imcContainer, userSharedPreferences)
        );

        updateFields(userWeight, userHeight, userBodyMassIndex, userBodyMassIndexUnit, imcContainer, userSharedPreferences);

        defineMyProfileButton.setOnClickListener(v -> activityLauncher.launch(new Intent(getActivity(), HealthInformationActivity.class)));

        return view;
    }

    private void updateFields(TextView userWeight, TextView userHeight, TextView userBodyMassIndex, TextView userBodyMassIndexUnit, LinearLayout imcContainer, UserSharedPreferences userSharedPreferences)
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
    }
}
