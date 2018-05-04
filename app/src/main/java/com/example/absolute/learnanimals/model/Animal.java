package com.example.absolute.learnanimals.model;

/**
 * Created by Absolute on 4/18/2018.
 */

public class Animal {

    private String name;
    private int res;

    private String imageLink;

    private int soundRawRes;

    public Animal(String name, int res, int sound) {
        this.name = name;
        this.res = res;

        this.soundRawRes = sound;
    }

    public Animal(String name, String imageLink){
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getSoundRawRes() {
        return soundRawRes;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setSoundRawRes(int soundRawRes) {
        this.soundRawRes = soundRawRes;
    }
}
