package com.example.myfoodapp.models;

import java.util.List;

public class ProductDetailModel {
    private String name;
    private String category;
    private String image_name;
    private float price;
    private float rating;
    private String timing;
    private String description;
    private String ingredients;
    private List<ReviewModel> reviews;

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getImage_name() { return image_name; }
    public float getPrice() { return price; }
    public float getRating() { return rating; }
    public String getTiming() { return timing; }
    public String getDescription() { return description; }
    public String getIngredients() { return ingredients; }
    public List<ReviewModel> getReviews() { return reviews; }
}
