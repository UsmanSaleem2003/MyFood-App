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
import com.example.myfoodapp.models.HomeHorModel;
import com.example.myfoodapp.models.HomeVerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateVerticalRec {

    RecyclerView homeHorizontalRec, homeVerticalRec;
    ArrayList<HomeHorModel> HomeHorModelList;
    HomeHorAdapter homeHorAdapter;

    ArrayList<HomeVerModel> HomeVerModelList;
    HomeVerAdapter homeVerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);

        HomeHorModelList = new ArrayList<>();
        HomeHorModelList.add(new HomeHorModel(R.drawable.pizza, "pizza"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.hamburger, "burger"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.fries, "fries"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.ice_cream, "ice_cream"));
        HomeHorModelList.add(new HomeHorModel(R.drawable.sandwich, "sandwich"));

        homeHorAdapter = new HomeHorAdapter(this, getActivity(), HomeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        HomeVerModelList = new ArrayList<>();
        homeVerAdapter = new HomeVerAdapter(getActivity(), HomeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        loadVerticalData("pizza"); // Default load

        return root;
    }

    private void loadVerticalData(String category) {
        FirebaseDatabase.getInstance().getReference("products")
                .orderByChild("category").equalTo(category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<HomeVerModel> updatedList = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            String name = child.child("name").getValue(String.class);
                            String imageName = child.child("imageUrl").getValue(String.class);
                            Double priceVal = child.child("price").getValue(Double.class);
                            String price = "Min - $" + (priceVal != null ? priceVal : 0);
                            String timing = "10:00 - 23:00";  // default or use Firebase if dynamic
                            String rating = "4.9";  // default or use child.get("rating")

                            updatedList.add(new HomeVerModel(name, timing, rating, price, imageName));
                        }
                        homeVerAdapter.updateList(updatedList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FIREBASE_DB", "Error: " + error.getMessage());
                    }
                });
    }

    @Override
    public void callback(int position) {
        String category = HomeHorModelList.get(position).getName().toLowerCase();
        loadVerticalData(category);
    }

    @Override
    public void callback(int position, ArrayList<HomeVerModel> list) {

    }
}
