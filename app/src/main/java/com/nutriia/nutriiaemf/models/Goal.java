package com.nutriia.nutriiaemf.models;

/**
 * Model for goal
 */
public class Goal {
    private int imageResId;

    private String text;

    private String description;

    /**
     * Indicates if the goal is actual
     */
    private boolean isActual;

    public Goal(int imageResId, String text, String description) {
        this(imageResId, text, description, false);
    }

    public Goal(int imageResId, String text, String description, boolean isActual) {
        this.imageResId = imageResId;
        this.text = text;
        this.description = description;
        this.isActual = isActual;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getDescription() { return description; }

    public boolean isActual() { return isActual; }

    public void setActual(boolean actual) { isActual = actual; }
}
