package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.Arrays;
import java.util.List;

public class PageTitle extends AppFragment {

    public enum ActivityType {
        MAIN,
        FORMATION,
        ANALYSIS
    }

    private final ActivityType activityType;
    private View view;
    private Context context;

    public PageTitle(ActivityType activityType) {
        super();
        this.activityType = activityType;
    }


    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.component_page_title, frameLayout, false);

        TextView title = view.findViewById(R.id.page_title);

        if (activityType == ActivityType.FORMATION) {
            title.setText(getFormationPageTitle());
        } else if (activityType == ActivityType.ANALYSIS) {
            title.setText(getAnalysisPageTitle());
        } else {
            title.setText(getMainPageTitle());
        }

        frameLayout.addView(view);
    }

    private String getMainPageTitle() {
        return context.getResources().getString(R.string.page_title_welcome);
    }

    private String getFormationPageTitle() {
        return context.getResources().getString(R.string.formation_page_title);
    }

    private String getAnalysisPageTitle() {
        return context.getResources().getString(R.string.analysis_page_title);
    }
}
