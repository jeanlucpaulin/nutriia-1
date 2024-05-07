package com.nutriia.nutriia;

public class ButtonObjectif {
    private int imageResId;
    private String text;

    public ButtonObjectif(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}