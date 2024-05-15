package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.ObjectifActivity;
import com.nutriia.nutriia.activities.RedefineGoalActivity;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;

public class DefineMyGoal extends Fragment {

    private AppCompatActivity activity;
    private onActivityFinishListener activityFinishListener;
    private ActivityResultLauncher<Intent> activityLauncher;

    public DefineMyGoal(AppCompatActivity activity, onActivityFinishListener activityFinishListener) {
        this.activity = activity;
        this.activityFinishListener = activityFinishListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(activity != null)
        {
            activityLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (activityFinishListener != null) activityFinishListener.onActivityFinish();
                    }
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.define_set_my_goal, container, false);

        Button defineMyGoalButton = view.findViewById(R.id.define_goal);

        defineMyGoalButton.setOnClickListener(v -> {
            if(activityLauncher != null) {
                activityLauncher.launch(new Intent(getActivity(), RedefineGoalActivity.class));
            }
            else {
                startActivity(new Intent(getActivity(), RedefineGoalActivity.class));
            }
        });

        return view;
    }
}

