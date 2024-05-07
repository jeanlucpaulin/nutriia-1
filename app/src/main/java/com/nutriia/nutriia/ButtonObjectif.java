package com.nutriia.nutriia;

public class ButtonObjectif {
    private int imageResId;
    private String text;
    private boolean selected;

    public ButtonObjectif(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
        this.selected = false;
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
}
