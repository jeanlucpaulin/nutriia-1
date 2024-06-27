package com.nutriia.nutriiaemf.fragments;

import android.content.Context;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nutriia.nutriiaemf.models.Day;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.builders.DayBuilder;
import com.nutriia.nutriiaemf.interfaces.OnValidateDay;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

import java.util.Map;
import java.util.Set;

public class MyDayAnalysis extends AppFragment implements OnValidateDay {

    private TextView textViewAnalysis;
    private View view;

    private Context context;

    @Override
    public void create(FrameLayout frameLayout) {

        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.component_my_day_analysis, frameLayout, false);

        Day day = new DayBuilder().buildOnlyWithGoal(UserSharedPreferences.getInstance(context));

        textViewAnalysis = view.findViewById(R.id.textViewAnalysis);

        onValidateDayResponse(day);

        frameLayout.addView(view);
    }

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        setLoading();
    }

    private void setLoading(){
        float radius = 20f;
        RenderEffect renderEffect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP);
            textViewAnalysis.setRenderEffect(renderEffect);
        }
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void removeLoading(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            textViewAnalysis.setRenderEffect(null);
        }
        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    public void onValidateDayResponse(Day day) {
        if(day == null) Log.d("MyDayAnalysis", "Day is null");
        else textViewAnalysis.setText(day.getAnalysis());
        removeLoading();
    }
}
