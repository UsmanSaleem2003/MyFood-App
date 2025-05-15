package com.example.myfoodapp.models;

import com.google.gson.annotations.SerializedName;

public class HomeVerModel {
    private int id;
    private String name;
    private String timing;
    private float rating;
    private float price;

    @SerializedName("image_name")
    private String imageName;

    public HomeVerModel() {
        // Required for Firebase and Retrofit
    }

    public HomeVerModel(int id, String name, String timing, float price, float rating, String imageName) {
        this.id = id;
        this.name = name;
        this.timing = timing;
        this.price = price;
        this.rating = rating;
        this.imageName = imageName;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getTiming() { return timing; }
    public float getRating() { return rating; }
    public float getPrice() { return price; }
    public String getImageName() { return imageName; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTiming(String timing) { this.timing = timing; }
    public void setRating(float rating) { this.rating = rating; }
    public void setPrice(float price) { this.price = price; }
    public void setImageName(String imageName) { this.imageName = imageName; }
}
