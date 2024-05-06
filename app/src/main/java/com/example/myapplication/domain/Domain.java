package com.example.myapplication.domain;

import java.io.Serializable;

public class Domain implements Serializable {
    private String title;
    private int picUrl;

    private String recipe;

    public Domain(String title, String picUrl, String recipe) {
        this.title = title;
        this.picUrl = Integer.parseInt(picUrl);
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
