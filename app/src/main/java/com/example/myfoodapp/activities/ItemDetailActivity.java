package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView img = findViewById(R.id.detail_img);
        TextView name = findViewById(R.id.detail_name);
        TextView desc = findViewById(R.id.detail_description);
        TextView rating = findViewById(R.id.detail_rating);
        TextView timing = findViewById(R.id.detail_timing);
        TextView price = findViewById(R.id.detail_price);
        Button addToCart = findViewById(R.id.detail_add_to_cart);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name.setText(extras.getString("name", ""));
            desc.setText("Description"); // Placeholder — add real one if available
            rating.setText(extras.getString("rating", ""));
            timing.setText(extras.getString("timing", ""));
            price.setText("$" + extras.getString("price", ""));

            int imageResId = getResources().getIdentifier(
                    extras.getString("image", ""),
                    "drawable",
                    getPackageName());

            img.setImageResource(imageResId != 0 ? imageResId : R.drawable.placeholder);
        }

        addToCart.setOnClickListener(v ->
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show());
    }
}
