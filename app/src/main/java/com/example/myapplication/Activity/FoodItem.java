package com.example.myapplication.Activity;

public class FoodItem {
    private  int imageRecourse;
    private String tittle;
    private String key_id;
    private String favStatus;

    public FoodItem(){

    }

    public FoodItem(int imageRecourse, String tittle, String key_id, String favStatus) {
        this.imageRecourse = imageRecourse;
        this.tittle = tittle;
        this.key_id = key_id;
        this.favStatus = favStatus;
    }

    public int getImageRecourse() {
        return imageRecourse;
    }

    public void setImageRecourse(int imageRecourse) {
        this.imageRecourse = imageRecourse;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }
}
