package com.nutriia.nutriia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.ObjectifActivity;

public class DefineMyGoal extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.define_set_my_goal, container, false);

        Button defineMyGoalButton = view.findViewById(R.id.define_goal);

        defineMyGoalButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ObjectifActivity.class));
        });

        return view;
    }
}
