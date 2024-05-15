package com.nutriia.nutriia;

public class Goal {
    private int imageResId;
    private String text;
    private boolean selected;

    private boolean isActual;

    public Goal(int imageResId, String text) {
        this(imageResId, text, false);
    }

    public Goal(int imageResId, String text, boolean isActual) {
        this.imageResId = imageResId;
        this.text = text;
        this.selected = false;
        this.isActual = isActual;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isActual() { return isActual; }
}
