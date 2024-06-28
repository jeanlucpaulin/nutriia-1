package com.nutriia.nutriiaemf.utils;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.nutriia.nutriiaemf.activities.CoachActivity;
import com.nutriia.nutriiaemf.activities.DayAnalysisActivity;
import com.nutriia.nutriiaemf.activities.FormationActivity;
import com.nutriia.nutriiaemf.activities.MainActivity;
import com.nutriia.nutriiaemf.activities.MeetActivity;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.detectors.SwipeGestureDetector;


/**
 *
 * This class is used to manage the navigation bar of the application.
 * You can define the navigation bar in the layout of the activity and then call the init method to initialize the navigation bar.
 * The init method will set the listener for each item of the navigation bar.
 *
 */

public class NavBarListener {

    private static AppCompatActivity activity;

    public static void init(AppCompatActivity activity, int current){
        NavBarListener.activity = activity;

        /* Set the listener for item "eventTarget" */
        LinearLayout layout = activity.findViewById(R.id.navbar_target);
        if (layout != null) {
            if (R.id.navbar_target == current) {
                TextView text = ((TextView) activity.findViewById(R.id.navbar_target_text));
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_target_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_target_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventTarget);
        }

        /* Set the listener for item "eventAnalysis" */
        layout = activity.findViewById(R.id.navbar_analysis);
        if (layout != null) {
            if (R.id.navbar_analysis == current) {
                TextView text = activity.findViewById(R.id.navbar_analysis_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_analysis_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_leaves_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventAnalysis);
        }

        /* Set the listener for item "eventMeets" */
        layout = activity.findViewById(R.id.navbar_meet);
        if (layout != null) {
            if (R.id.navbar_meet == current) {
                TextView text = activity.findViewById(R.id.navbar_meet_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_meet_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_chat_advisor_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventMeets);
        }

        /* Set the listener for item "eventCoach" */
        layout = activity.findViewById(R.id.navbar_learn);
        if (layout != null) {
            if (R.id.navbar_learn == current) {
                TextView text = activity.findViewById(R.id.navbar_learn_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_learn_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_formation_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventLearn);
        }
    }

    /**
     * This section contains the listener for each item of the navigation bar.
     * Each listener will call the navigate method to switch to the corresponding activity.
     */
    private static void eventTarget(View v){
        navigate(v, MainActivity.class);
    }

    private static void eventAnalysis(View v){
        navigate(v, DayAnalysisActivity.class);
    }

    private static void eventMeets(View v){
        navigate(v, MeetActivity.class);
    }

    private static void eventLearn(View v){
        navigate(v, FormationActivity.class);
    }


    /**
     * This method is used to switch to the activity corresponding to the activityClass parameter.
     * @param v The view that triggered the event
     * @param activityClass The class of the activity to switch to
     */
    private static void navigate(View v, Class<?> activityClass){
        Intent intent = new Intent(activity, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

/**
     * This method is used to switch to the activity corresponding to the activityClass parameter. This method is currently unused.
     * @param activity The activity to switch to
     * @param direction The direction of the swipe
     */
    public static void swipeActivity(AppCompatActivity activity, SwipeGestureDetector.SwipeDirection direction) {
        switch (direction) {
            case LEFT:
                if (activity instanceof MainActivity) {
                    eventAnalysis(activity.findViewById(R.id.navbar_analysis));
                } else if (activity instanceof DayAnalysisActivity) {
                    eventMeets(activity.findViewById(R.id.navbar_meet));
                } else if (activity instanceof MeetActivity) {
                    eventLearn(activity.findViewById(R.id.navbar_learn));
                }
                break;
            case RIGHT:
                if (activity instanceof DayAnalysisActivity) {
                    eventTarget(activity.findViewById(R.id.navbar_target));
                } else if (activity instanceof MeetActivity) {
                    eventAnalysis(activity.findViewById(R.id.navbar_analysis));
                } else if (activity instanceof FormationActivity) {
                    eventMeets(activity.findViewById(R.id.navbar_meet));
                }
                break;
        }
    }
}
