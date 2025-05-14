package com.example.myfoodapp.models;

public class ReviewModel {
    private int id;
    private String text;

    public int getId() { return id; }
    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
    }
}
