package com.example.myfoodapp.models;

public class CartItemModel {
    private String name, imageName, timing;
    private double price;
    private int quantity;

    public CartItemModel(String name, String imageName, String timing, double price, int quantity) {
        this.name = name;
        this.imageName = imageName;
        this.timing = timing;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getImageName() { return imageName; }
    public String getTiming() { return timing; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
