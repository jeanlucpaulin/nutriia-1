package com.nutriia.nutriia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.nutriia.nutriia.adapters.SlideAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private SlideAdapter slideAdapter;
    private List<Slide> slides;

    private ImageButton previousButton;

    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_tips);

        viewPager = findViewById(R.id.viewPager);

        // Exemple of a list of slides
        //TODO : DELETE THIS EXAMPLE
        slides = new ArrayList<>();
        slides.add(new Slide("Title 1", "Description 1"));
        slides.add(new Slide("Title 2", "Description 2"));
        slides.add(new Slide("Title 3", "Description 3"));

        slideAdapter = new SlideAdapter(this, slides);

        viewPager.setAdapter(slideAdapter);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        // Previous and next buttons to navigate through the slides
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
    }
}
