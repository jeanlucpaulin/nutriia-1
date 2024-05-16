package com.nutriia.nutriia;

public class Goal {
    private int imageResId;
    private String text;

    private String description;
    private boolean selected;

    private boolean isActual;

    public Goal(int imageResId, String text, String description) {
        this(imageResId, text, description, false);
    }

    public Goal(int imageResId, String text, String description, boolean isActual) {
        this.imageResId = imageResId;
        this.text = text;
        this.description = description;
        this.selected = false;
        this.isActual = isActual;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getDescription() { return description; }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isActual() { return isActual; }
}
