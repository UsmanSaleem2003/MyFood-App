package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;
import com.example.myfoodapp.api.ProductApiService;
import com.example.myfoodapp.api.RetrofitClient;
import com.example.myfoodapp.models.ProductDetailModel;
import com.example.myfoodapp.models.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailActivity extends AppCompatActivity {

    private ProductApiService apiService;
    private LinearLayout reviewsContainer;
    private EditText reviewInput;
    private int productId = -1;

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
        TextView ingredients = findViewById(R.id.detail_ingredients);
        Button addToCart = findViewById(R.id.detail_add_to_cart);
        reviewInput = findViewById(R.id.review_input);
        Button submitReview = findViewById(R.id.submit_review_btn);
        reviewsContainer = findViewById(R.id.reviews_container);

        productId = getIntent().getIntExtra("product_id", -1);

        apiService = RetrofitClient.getInstance().create(ProductApiService.class);

        apiService.getProductDetail(productId).enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductDetailModel> call, @NonNull Response<ProductDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailModel product = response.body();
                    name.setText(product.getName());
                    desc.setText(product.getDescription());
                    rating.setText(String.valueOf(product.getRating()));
                    timing.setText(product.getTiming());
                    price.setText("$" + product.getPrice());
                    ingredients.setText(product.getIngredients());

                    int imageResId = getResources().getIdentifier(
                            product.getImage_name().toLowerCase(), "drawable", getPackageName());
                    img.setImageResource(imageResId != 0 ? imageResId : R.drawable.placeholder);

                    renderReviews(product.getReviews());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductDetailModel> call, Throwable t) {
                Toast.makeText(ItemDetailActivity.this, "Error loading product", Toast.LENGTH_SHORT).show();
            }
        });

        submitReview.setOnClickListener(v -> {
            String text = reviewInput.getText().toString().trim();
            if (text.isEmpty()) {
                reviewInput.setError("Review cannot be empty");
                return;
            }

            ReviewModel review = new ReviewModel();
//            review.text = text;  // Make public or add setter
            review.setText(text);
            apiService.submitReview(productId, review).enqueue(new Callback<ReviewModel>() {
                @Override
                public void onResponse(@NonNull Call<ReviewModel> call, @NonNull Response<ReviewModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        addReviewView(response.body().getText());
                        reviewInput.setText("");
                        Toast.makeText(ItemDetailActivity.this, "Review added!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReviewModel> call, Throwable t) {
                    Toast.makeText(ItemDetailActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                }
            });
        });

        addToCart.setOnClickListener(v ->
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show());
    }

    private void renderReviews(List<ReviewModel> reviews) {
        reviewsContainer.removeAllViews();
        for (ReviewModel review : reviews) {
            addReviewView(review.getText());
        }
    }

    private void addReviewView(String reviewText) {
        TextView reviewView = new TextView(this);
        reviewView.setText("- " + reviewText);
        reviewView.setPadding(20, 10, 20, 10);
        reviewView.setBackgroundResource(R.drawable.review_box_bg);
        reviewView.setTextColor(getResources().getColor(R.color.white));

        // ✅ Add bottom margin to each review
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 12); // left, top, right, bottom
        reviewView.setLayoutParams(params);

        reviewsContainer.addView(reviewView);
    }
}
