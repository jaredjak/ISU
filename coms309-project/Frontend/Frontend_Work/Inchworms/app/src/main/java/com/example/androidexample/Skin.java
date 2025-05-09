package com.example.androidexample;

import android.content.Context;

public class Skin {
    private String color;
//    private String imageURL;
    private int count;

    public Skin(String color, int count) {
        this.color = color;
//        this.imageURL = imageURL;
        this.count = count;
    }

    public String getColor() {return color;}
//    public String getImageUrl() {return imageURL;}
    public int getCount() {return count;}

//    public void setCount() {this.count = count;}

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(color.toLowerCase(), "drawable", context.getPackageName());
    }
}
