package com.example.myfoodapp.models;

import com.google.gson.annotations.SerializedName;

public class HomeVerModel {
    private int id;
    private String name;
    private String timing;
    private String rating;
    private String price;
    @SerializedName("image_name")
    private String imageName;

    public int getId() { return id; }
    public String getImageName() { return imageName; }
    public String getName() { return name; }
    public String getTiming() { return timing; }
    public String getRating() { return rating; }
    public String getPrice() { return price; }
}
