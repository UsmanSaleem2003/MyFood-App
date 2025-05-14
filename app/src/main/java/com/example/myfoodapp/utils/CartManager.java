package com.example.myfoodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myfoodapp.models.CartItemModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "my_food_cart";
    private static final String CART_KEY = "cart_items";

    private static List<CartItemModel> cartItems = new ArrayList<>();

    public static void initialize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<CartItemModel>>() {}.getType();
            cartItems = new Gson().fromJson(json, type);
        }
    }

    public static void saveCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(cartItems);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    public static void addItem(Context context, CartItemModel item) {
        for (CartItemModel existing : cartItems) {
            if (existing.getName().equals(item.getName())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                saveCart(context);
                return;
            }
        }
        cartItems.add(item);
        saveCart(context);
    }

    public static List<CartItemModel> getCartItems() {
        return cartItems;
    }

    public static void clearCart(Context context) {
        cartItems.clear();
        saveCart(context);
    }

    public static double calculateTotal() {
        double total = 0;
        for (CartItemModel item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
