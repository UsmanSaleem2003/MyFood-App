package com.example.myfoodapp.api;

import com.example.myfoodapp.models.HomeVerModel;
import com.example.myfoodapp.models.ProductDetailModel;
import com.example.myfoodapp.models.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApiService {
    @GET("/products/{category}")
    Call<List<HomeVerModel>> getProductsByCategory(@Path("category") String category);

    @GET("product/{id}")
    Call<ProductDetailModel> getProductDetail(@Path("id") int id);

    @POST("product/{id}/review")
    Call<ReviewModel> submitReview(@Path("id") int id, @Body ReviewModel review);

    @GET("search")
    Call<List<HomeVerModel>> searchProducts(@Query("q") String query);

}
