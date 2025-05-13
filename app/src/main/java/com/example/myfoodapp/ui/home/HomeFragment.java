package com.example.myfoodapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.HomeHorAdapter;
import com.example.myfoodapp.adapters.HomeVerAdapter;
import com.example.myfoodapp.adapters.UpdateVerticalRec;
import com.example.myfoodapp.api.ProductApiService;
import com.example.myfoodapp.api.RetrofitClient;
import com.example.myfoodapp.models.HomeHorModel;
import com.example.myfoodapp.models.HomeVerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements UpdateVerticalRec {

    RecyclerView homeHorizontalRec, homeVerticalRec;
    ArrayList<HomeHorModel> HomeHorModelList;
    HomeHorAdapter homeHorAdapter;

    ArrayList<HomeVerModel> HomeVerModelList;
    HomeVerAdapter homeVerAdapter;

    ProductApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // ✅ First init Retrofit before adapter creation
        apiService = RetrofitClient.getInstance().create(ProductApiService.class);

        // RecyclerView for horizontal
        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        HomeHorModelList = new ArrayList<>();
        HomeHorModelList.add(new HomeHorModel(R.drawable.pizza, "pizza"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.hamburger, "burger"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.fries, "fries"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.ice_cream, "ice_cream"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.sandwich, "sandwich"));

        homeHorAdapter = new HomeHorAdapter(this, getActivity(), HomeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        // RecyclerView for vertical
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);
        HomeVerModelList = new ArrayList<>();
        homeVerAdapter = new HomeVerAdapter(getActivity(), HomeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        // ✅ Default category
        loadVerticalData("pizza");

        return root;
    }

    private void loadVerticalData(String category) {
        if (apiService == null) {
            Log.e("API_INIT", "apiService is null!");
            return;
        }

        Call<List<HomeVerModel>> call = apiService.getProductsByCategory(category);
        call.enqueue(new Callback<List<HomeVerModel>>() {
            @Override
            public void onResponse(Call<List<HomeVerModel>> call, Response<List<HomeVerModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HomeVerModel> data = response.body();
                    Log.d("API_SUCCESS", "Loaded " + data.size() + " items for category " + category);
                    HomeVerModelList.clear();
                    HomeVerModelList.addAll(data);
                    homeVerAdapter.updateList(HomeVerModelList);
                } else {
                    Log.e("API_FAIL", "Response Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<HomeVerModel>> call, Throwable t) {
                Log.e("API_FAIL", "Request Failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void callback(int position) {
        String category = HomeHorModelList.get(position).getName().toLowerCase();
        loadVerticalData(category);
    }
}
