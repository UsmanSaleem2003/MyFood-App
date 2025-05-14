package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.HomeVerAdapter;
import com.example.myfoodapp.api.ProductApiService;
import com.example.myfoodapp.api.RetrofitClient;
import com.example.myfoodapp.models.HomeVerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView searchRecycler;
    private HomeVerAdapter adapter;
    private List<HomeVerModel> resultList = new ArrayList<>();
    private ProductApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = findViewById(R.id.search_input);
        searchRecycler = findViewById(R.id.search_result_list);
        adapter = new HomeVerAdapter(this, new ArrayList<>());
        searchRecycler.setAdapter(adapter);
        searchRecycler.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getInstance().create(ProductApiService.class);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    searchQuery(s.toString());
                } else {
                    adapter.updateList(new ArrayList<>());
                }
            }
        });
    }

    private void searchQuery(String query) {
        Call<List<HomeVerModel>> call = apiService.searchProducts(query);
        call.enqueue(new Callback<List<HomeVerModel>>() {
            @Override
            public void onResponse(Call<List<HomeVerModel>> call, Response<List<HomeVerModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateList(new ArrayList<>(response.body()));
                } else {
                    Log.e("SEARCH", "No results: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<HomeVerModel>> call, Throwable t) {
                Log.e("SEARCH", "Error: " + t.getMessage());
            }
        });
    }
}
