package com.example.myapplication.domain;

public class AddRecipeModel {
    private String name;
    private String category;
    private String description;
    private String imageAlpha;

    public AddRecipeModel() {
    }

    public AddRecipeModel(String name, String category, String description, String imageAlpha) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.imageAlpha = imageAlpha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageAlpha() {
        return imageAlpha;
    }

    public void setImageAlpha(String imageAlpha) {
        this.imageAlpha = imageAlpha;
    }
}
