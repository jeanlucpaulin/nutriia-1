package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.Arrays;
import java.util.List;

public class PageTitle extends Fragment {

    public enum ActivityType {
        MAIN,
        FORMATION
    }

    private final ActivityType activityType;
    private View view;

    public PageTitle(ActivityType activityType) {
        this.activityType = activityType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.component_page_title, container, false);

        TextView title = view.findViewById(R.id.page_title);

        if (activityType == ActivityType.FORMATION) {
            title.setText(getFormationPageTitle());
        } else {
            title.setText(getMainPageTitle());
        }

        return view;
    }

    private String getMainPageTitle() {
        return getResources().getString(R.string.page_title_welcome);
    }

    private String getFormationPageTitle() {
        return getResources().getString(R.string.formation_page_title);
    }
}
