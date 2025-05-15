package com.example.myfoodapp.models;

import java.util.List;

public class ProductDetailModel {
    private int id;
    private String name;
    private String description;
    private String ingredients;
    private String image_name;
    private String timing;
    private double price;
    private double rating;
    private boolean isFavorite;

    private List<ReviewModel> reviews;  // ✅ newly added

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIngredients() { return ingredients; }
    public String getImage_name() { return image_name; }
    public String getTiming() { return timing; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public boolean isFavorite() { return isFavorite; }

    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public List<ReviewModel> getReviews() { return reviews; }
    public void setReviews(List<ReviewModel> reviews) { this.reviews = reviews; }
}
