package com.example.myapplication.domain;

import android.widget.ImageView;

import java.io.Serializable;

public class FavItem extends Domain implements Serializable {

    private String tittle;
    private ImageView imageView;
    private ImageView favBtn;

    public FavItem(String title, int picUrl, String recipe, String tittle, ImageView imageView, ImageView favBtn) {
        super(title, picUrl, recipe);
        this.tittle = tittle;
        this.imageView = imageView;
        this.favBtn = favBtn;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
