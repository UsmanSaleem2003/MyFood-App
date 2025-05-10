package com.example.myfoodapp.models;

public class DailyMealModel {

    int image;
    String name,discount,discription,type;


    public DailyMealModel(int image, String name, String discount, String discription, String type) {
        this.image = image;
        this.name = name;
        this.discount = discount;
        this.discription = discription;
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
