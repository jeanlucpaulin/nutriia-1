package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.Slide;
import com.nutriia.nutriia.adapters.SlideAdapter;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipsAdvices extends AppFragment implements OnNewGoalSelected {
    private ViewPager2 viewPager;
    private SlideAdapter slideAdapter;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private final List<Slide> slides = new ArrayList<>();

    private Context context;

    private Context getContext() {
        return context;
    }


    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.component_tips, frameLayout, false);

        viewPager = view.findViewById(R.id.viewPager);

        int goalIndex = UserSharedPreferences.getInstance(getContext()).getGoal();

        updateTips(goalIndex);


        previousButton = view.findViewById(R.id.previousButton);
        nextButton = view.findViewById(R.id.nextButton);

        previousButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            } else if (!slides.isEmpty()) {
                viewPager.setCurrentItem(slides.size() - 1);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < slides.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            else {
                viewPager.setCurrentItem(0);
            }
        });

        frameLayout.addView(view);
    }

    @Override
    public void onNewGoalSelected(int position) {
        updateTips(position);
    }

    private void updateTips(int goalIndex) {
        List<String> tipsNames = Arrays.asList(getContext().getResources().getStringArray(R.array.tips_names));


        List<String> tipsTitles = Arrays.asList(getContext().getResources().getStringArray(R.array.tips_titles));

        if(slides.isEmpty()) {
            for(int i = 0; i < tipsNames.size(); i++) {
                List<String> tips = Arrays.asList(getContext().getResources().getStringArray(getContext().getResources().getIdentifier(tipsNames.get(i), "array", getContext().getPackageName())));
                slides.add(new Slide(tipsTitles.get(i), tips.get(goalIndex)));
            }

            slideAdapter = new SlideAdapter(getContext(), slides);

            viewPager.setAdapter(slideAdapter);
        }
        else {
            for(int i = 0; i < tipsNames.size(); i++) {
                List<String> tips = Arrays.asList(getContext().getResources().getStringArray(getContext().getResources().getIdentifier(tipsNames.get(i), "array", getContext().getPackageName())));
                slides.get(i).setDescription(tips.get(goalIndex));
            }

            slideAdapter.notifyDataSetChanged();
        }
    }
}
