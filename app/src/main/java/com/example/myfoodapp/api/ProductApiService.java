package com.example.myfoodapp.api;

import com.example.myfoodapp.models.HomeVerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApiService {
    @GET("products/{category}")
    Call<List<HomeVerModel>> getProductsByCategory(@Path("category") String category);
}
