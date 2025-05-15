package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;
import com.example.myfoodapp.api.ProductApiService;
import com.example.myfoodapp.api.RetrofitClient;
import com.example.myfoodapp.models.CartItemModel;
import com.example.myfoodapp.models.HomeVerModel;
import com.example.myfoodapp.models.ProductDetailModel;
import com.example.myfoodapp.models.ReviewModel;
import com.example.myfoodapp.utils.CartManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailActivity extends AppCompatActivity {

    private ProductApiService apiService;
    private LinearLayout reviewsContainer;
    private EditText reviewInput;
    private ImageButton favoriteBtn;
    private int productId = -1;

    private String productName, imageName, productTiming;
    private float productPrice, productRating;

    private boolean isFavorite = false;
    private DatabaseReference favRef;

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
        favoriteBtn = findViewById(R.id.favorite_btn);

        productId = getIntent().getIntExtra("product_id", -1);
        apiService = RetrofitClient.getInstance().create(ProductApiService.class);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favRef = FirebaseDatabase.getInstance().getReference("favorites").child(userId);

        checkFavoriteStatus();

        favoriteBtn.setOnClickListener(v -> {
            if (isFavorite) {
                favRef.child(String.valueOf(productId)).removeValue();
                favoriteBtn.setImageResource(R.drawable.ic_favorite_border);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                HomeVerModel model = new HomeVerModel(
                        productId,
                        productName,
                        productTiming,
                        productPrice,
                        productRating,
                        imageName
                );
                favRef.child(String.valueOf(productId)).setValue(model);
                favoriteBtn.setImageResource(R.drawable.ic_favorite_filled);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            isFavorite = !isFavorite;
        });

        apiService.getProductDetail(productId).enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductDetailModel> call, @NonNull Response<ProductDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailModel product = response.body();

                    productName = product.getName();
                    imageName = product.getImage_name();
                    productTiming = product.getTiming();
                    productPrice = (float) product.getPrice();
                    productRating = (float) product.getRating();

                    name.setText(productName);
                    desc.setText(product.getDescription());
                    rating.setText(String.valueOf(productRating));
                    timing.setText(productTiming);
                    price.setText("$" + String.format("%.2f", productPrice));
                    ingredients.setText(product.getIngredients());

                    int imageResId = getResources().getIdentifier(
                            imageName.toLowerCase().replace(".jpg", "").replace(".png", ""),
                            "drawable",
                            getPackageName());

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

        addToCart.setOnClickListener(v -> {
            CartManager.addItem(this, new CartItemModel(
                    productName, imageName, productTiming, productPrice, 1
            ));
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkFavoriteStatus() {
        favRef.child(String.valueOf(productId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isFavorite = snapshot.exists();
                favoriteBtn.setImageResource(isFavorite ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemDetailActivity.this, "Could not load favorite status", Toast.LENGTH_SHORT).show();
            }
        });
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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16);
        reviewView.setLayoutParams(params);

        reviewsContainer.addView(reviewView);
    }
}
