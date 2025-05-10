package com.example.myfoodapp.ui.MyCart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.CartAdapter;
import com.example.myfoodapp.models.CartModel;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {

    List<CartModel> list;
    CartAdapter cartAdapter;
    RecyclerView recyclerView;

    public MyCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        list.add(new CartModel(R.drawable.s1,"order 1","$30","4.5"));
        list.add(new CartModel(R.drawable.s2,"order 2","$40","4.8"));
        list.add(new CartModel(R.drawable.fav1,"order 3","$35","4.2"));
        list.add(new CartModel(R.drawable.s1,"order 1","$30","4.5"));
        list.add(new CartModel(R.drawable.s2,"order 2","$40","4.8"));
        list.add(new CartModel(R.drawable.fav1,"order 3","$35","4.2"));

        cartAdapter = new CartAdapter(list);
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return view;
    }
}