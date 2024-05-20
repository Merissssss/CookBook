package com.example.myapplication.domain;

import java.io.Serializable;

public class AddRecipeModel implements Serializable {
    private String name;
    private String category;
    private String description;
    private String imageAlpha;
    private String title;
    private String productId;
    private int likesCount;
    private boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public AddRecipeModel() {
    }

    public AddRecipeModel(String name, String category, String description, String imageAlpha, String title, String productId) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.imageAlpha = imageAlpha;
        this.title = title;
        this.productId = productId;
        this.isLiked = false;
    }

    // Getters and setters
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
