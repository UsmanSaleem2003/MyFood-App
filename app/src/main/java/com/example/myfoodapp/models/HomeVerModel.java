package com.example.myfoodapp.models;

public class HomeVerModel {
    private String name;
    private String timing;
    private String rating;
    private String price;
    private String imageName;  // Drawable name

    public HomeVerModel(String name, String timing, String rating, String price, String imageName) {
        this.name = name;
        this.timing = timing;
        this.rating = rating;
        this.price = price;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }

    public String getTiming() {
        return timing;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
