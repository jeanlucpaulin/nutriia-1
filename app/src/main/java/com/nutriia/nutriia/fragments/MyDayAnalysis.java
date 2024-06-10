package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.builders.DayBuilder;
import com.nutriia.nutriia.interfaces.OnValidateDay;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.Map;
import java.util.Set;

public class MyDayAnalysis extends AppFragment implements OnValidateDay {

    private TextView textViewAnalysis;

    private Context context;

    @Override
    public void create(FrameLayout frameLayout) {

        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_my_day_analysis, frameLayout, false);

        Day day = new DayBuilder().buildOnlyWithGoal(UserSharedPreferences.getInstance(context));

        textViewAnalysis = view.findViewById(R.id.textViewAnalysis);

        onValidateDayResponse(day);

        frameLayout.addView(view);
    }

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {

    }

    @Override
    public void onValidateDayResponse(Day day) {
        if(day == null) Log.d("MyDayAnalysis", "Day is null");
        textViewAnalysis.setText(day.getAnalysis());
    }
}
