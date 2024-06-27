package com.nutriia.nutriiaemf.models;

public class Slide {
    private final String title;
    private String description;

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

    public void setDescription(String description) {
        this.description = description;
    }
}
