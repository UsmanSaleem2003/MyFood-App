package com.example.myfoodapp.utils;
//it's a singleton for globally local management of cart

import com.example.myfoodapp.models.CartItemModel;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItemModel> cartItems = new ArrayList<>();

    public static void addItem(CartItemModel item) {
        for (CartItemModel existing : cartItems) {
            if (existing.getName().equals(item.getName())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public static List<CartItemModel> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }

    public static double calculateTotal() {
        double total = 0;
        for (CartItemModel item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
