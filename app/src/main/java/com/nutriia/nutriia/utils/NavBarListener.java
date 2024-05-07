package com.nutriia.nutriia.utils;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.nutriia.nutriia.R;

public class NavBarListener {

    private static boolean isInit = false;

    public static void init(AppCompatActivity activity, int current){
        if(isInit) return;
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
        isInit = true;
    }

    private static void eventTarget(View v){
        Log.d("NavBarListener", "eventTarget: " + v.getId());
    }

    private static void eventAnalysis(View v){
        Log.d("NavBarListener", "eventAnalysis: " + v.getId());
    }

    private static void eventMeets(View v){
        Log.d("NavBarListener", "eventMeets: " + v.getId());
    }

    private static void eventCoach(View v){
        Log.d("NavBarListener", "eventCoach: " + v.getId());
    }

    private static void eventLearn(View v){
        Log.d("NavBarListener", "eventLearn: " + v.getId());
    }
}
