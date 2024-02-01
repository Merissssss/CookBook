package com.example.myapplication.domain;

import android.widget.ImageView;

import java.io.Serializable;

public class Domain implements Serializable {
    private String title;
    private int picUrl;

    private String recipe;

    public Domain(String title, int picUrl, String recipe) {
        this.title = title;
        this.picUrl = picUrl;
        this.recipe = recipe;
    }


    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(int picUrl) {
        this.picUrl = picUrl;
    }

}
