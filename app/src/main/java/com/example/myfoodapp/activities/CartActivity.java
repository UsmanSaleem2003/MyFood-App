package com.example.myfoodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.CartAdapter;
import com.example.myfoodapp.utils.CartManager;

public class CartActivity extends AppCompatActivity {

    private CartAdapter cartAdapter;
    private EditText couponInput;
    private TextView netTotalText, discountText, deliveryText, finalTotalText, totalItemsText;

    private boolean couponApplied = false;
    private static final double DELIVERY_CHARGE = 5.0;
    private static final String VALID_COUPON = "111";
    private double discountAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.cart_recycler);
        couponInput = findViewById(R.id.coupon_input);
        Button applyCouponBtn = findViewById(R.id.apply_coupon_btn);
        Button checkoutBtn = findViewById(R.id.checkout_btn);

        netTotalText = findViewById(R.id.net_total_text);
        discountText = findViewById(R.id.discount_text);
        deliveryText = findViewById(R.id.delivery_text);
        finalTotalText = findViewById(R.id.final_total_text);
        totalItemsText = findViewById(R.id.total_items_text);  // now from XML layout

        cartAdapter = new CartAdapter(this, CartManager.getCartItems(), this::updateTotal);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applyCouponBtn.setOnClickListener(v -> {
            String input = couponInput.getText().toString().trim();
            if (VALID_COUPON.equals(input)) {
                couponApplied = true;
                Toast.makeText(this, "Coupon applied! 70% discount", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid coupon", Toast.LENGTH_SHORT).show();
                couponApplied = false;
            }
            updateTotal();
        });

        checkoutBtn.setOnClickListener(v -> {
            if (CartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "Cart is already empty", Toast.LENGTH_SHORT).show();
            } else {
                CartManager.clearCart(this);
                cartAdapter.notifyDataSetChanged();
                couponApplied = false;
                couponInput.setText("");
                updateTotal();

                // ✅ Navigate to ThankYouActivity
                startActivity(new Intent(this, ThankYouActivity.class));
                finish();
            }
        });

        updateTotal();
    }

    private void updateTotal() {
        double netTotal = CartManager.calculateTotal();
        int totalItems = CartManager.getCartItems().stream().mapToInt(item -> item.getQuantity()).sum();

        discountAmount = couponApplied ? netTotal * 0.7 : 0;
        double finalTotal = netTotal - discountAmount + DELIVERY_CHARGE;

        netTotalText.setText("Net Total: $" + String.format("%.2f", netTotal));
        discountText.setText("Discount: $" + String.format("%.2f", discountAmount));
        deliveryText.setText("Delivery: $5.00");
        finalTotalText.setText("Final Total: $" + String.format("%.2f", finalTotal));
        totalItemsText.setText("Total Items: " + totalItems);
    }
}
