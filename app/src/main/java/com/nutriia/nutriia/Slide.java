package com.nutriia.nutriia;

public class Slide {
    private final String title;
    private final String description;

    public Slide(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
