package com.example.myapplication.Activity;

import java.util.SplittableRandom;

public class FavItem {
    private String item_tittle;
    private String key_id;
    private int item_image;
    public FavItem() {
    }

    public FavItem(String item_tittle, String key_id, int item_image) {
        this.item_tittle = item_tittle;
        this.key_id = key_id;
        this.item_image = item_image;
    }

    public String getItem_tittle() {
        return item_tittle;
    }

    public void setItem_tittle(String item_tittle) {
        this.item_tittle = item_tittle;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }
}
