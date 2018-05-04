package com.example.absolute.learnanimals.model;

/**
 * Created by Absolute on 5/2/2018.
 */

public class Puzzle {
    private int imageRes;
    private String animalName;
    private String[] answers;

    public Puzzle(int imageRes, String animalName, String[] answers) {
        this.imageRes = imageRes;
        this.animalName = animalName;
        this.answers = answers;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
