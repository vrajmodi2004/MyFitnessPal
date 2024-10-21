package com.example.myfitnesstracker;
public class Exercise {

    private String name;
    private String steps;
    private int imageResId;

    public Exercise(String name, String steps, int imageResId) {
        this.name = name;
        this.steps = steps;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public int getImageResId() {
        return imageResId;
    }
}
