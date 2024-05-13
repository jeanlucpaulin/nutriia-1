package com.nutriia.nutriia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.Slide;
import com.nutriia.nutriia.adapters.SlideAdapter;

import java.util.ArrayList;
import java.util.List;

public class TipsTricks extends Fragment {
    private ViewPager2 viewPager;
    private SlideAdapter slideAdapter;
    private List<Slide> slides;
    private ImageButton previousButton;
    private ImageButton nextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.component_tips, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        slides = new ArrayList<>();
        slides.add(new Slide("Title 1", "Description 1"));
        slides.add(new Slide("Title 2", "Description 2"));
        slides.add(new Slide("Title 3", "Description 3"));

        slideAdapter = new SlideAdapter(getContext(), slides);

        viewPager.setAdapter(slideAdapter);

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

        return view;
    }
}
