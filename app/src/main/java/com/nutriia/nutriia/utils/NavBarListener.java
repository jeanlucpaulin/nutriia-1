package com.nutriia.nutriia.utils;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.nutriia.nutriia.CoachActivity;
import com.nutriia.nutriia.activities.FormationActivity;
import com.nutriia.nutriia.activities.MainActivity;
import com.nutriia.nutriia.activities.MeetActivity;
import com.nutriia.nutriia.R;

public class NavBarListener {

    private static AppCompatActivity activity;

    public static void init(AppCompatActivity activity, int current){
        NavBarListener.activity = activity;
        LinearLayout layout = activity.findViewById(R.id.navbar_target);
        if (layout != null) {
            if (R.id.navbar_target == current) {
                TextView text = ((TextView) activity.findViewById(R.id.navbar_target_text));
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_target_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_target_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventTarget);
        }

        layout = activity.findViewById(R.id.navbar_analysis);
        if (layout != null) {
            if (R.id.navbar_analysis == current) {
                TextView text = activity.findViewById(R.id.navbar_analysis_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_analysis_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_leaves_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventAnalysis);
        }

        layout = activity.findViewById(R.id.navbar_meet);
        if (layout != null) {
            if (R.id.navbar_meet == current) {
                TextView text = activity.findViewById(R.id.navbar_meet_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_meet_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_calendar_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventMeets);
        }

        layout = activity.findViewById(R.id.navbar_coach);
        if (layout != null) {
            if (R.id.navbar_coach == current) {
                TextView text = activity.findViewById(R.id.navbar_coach_text);
                text.setTextColor(activity.getResources().getColor(R.color.lime, activity.getTheme()));
                text.setTypeface(ResourcesCompat.getFont(activity, R.font.montserrat_bold));
                ((ImageView) activity.findViewById(R.id.navbar_coach_image)).setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.menu_icon_chat_full, activity.getTheme()));
            } else layout.setOnClickListener(NavBarListener::eventCoach);
        }

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

    private static void eventTarget(View v){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private static void eventAnalysis(View v){
        Log.d("NavBarListener", "eventAnalysis: " + v.getId());
    }

    private static void eventMeets(View v){
        Intent intent = new Intent(activity, MeetActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private static void eventCoach(View v){
        Intent intent = new Intent(activity, CoachActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private static void eventLearn(View v){
        Intent intent = new Intent(activity, FormationActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
