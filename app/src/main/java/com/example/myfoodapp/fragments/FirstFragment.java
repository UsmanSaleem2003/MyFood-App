package com.example.myfoodapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.FeaturedAdapter;
import com.example.myfoodapp.adapters.FeaturedVerAdapter;
import com.example.myfoodapp.models.FeaturedModel;
import com.example.myfoodapp.models.FeaturedVerModel;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment
{
    //////////// horizontal recycler view

    List<FeaturedModel> featuredModelslist;
    RecyclerView recyclerView;
    FeaturedAdapter featuredAdapter;


    //////////// vertical recycler view ////////////

    List<FeaturedVerModel> featuredVerModelsList;
    RecyclerView recyclerView2;
    FeaturedVerAdapter featuredVerAdapter;




    public FirstFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.featured_hor_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        featuredModelslist = new ArrayList<>();

        featuredModelslist.add(new FeaturedModel(R.drawable.fav1,"featured 1","description 1"));
        featuredModelslist.add(new FeaturedModel(R.drawable.fav2,"featured 2","description 2"));
        featuredModelslist.add(new FeaturedModel(R.drawable.fav3,"featured 3","description 3"));
        featuredModelslist.add(new FeaturedModel(R.drawable.fav1,"featured 4","description 4"));
        featuredModelslist.add(new FeaturedModel(R.drawable.fav2,"featured 5","description 5"));
        featuredModelslist.add(new FeaturedModel(R.drawable.fav3,"featured 6","description 6"));

        featuredAdapter = new FeaturedAdapter(featuredModelslist);
        recyclerView.setAdapter(featuredAdapter);


        //////////// vertical recycler view ////////////

        recyclerView2 = view.findViewById(R.id.featured_ver_rec);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        featuredVerModelsList = new ArrayList<>();

        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver1,"Featured 1","Description 1","10:00 - 8:00","4.8"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver2,"Featured 2","Description 2","10:00 - 6:00","4.2"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver3,"Featured 3","Description 3","10:00 - 11:00","4.4"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver1,"Featured 4","Description 4","10:00 - 8:00","4.8"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver2,"Featured 5","Description 5","10:00 - 6:00","4.2"));
        featuredVerModelsList.add(new FeaturedVerModel(R.drawable.ver3,"Featured 6","Description 6","10:00 - 11:00","4.4"));

        featuredVerAdapter = new FeaturedVerAdapter((featuredVerModelsList));
        recyclerView2.setAdapter(featuredVerAdapter);

        return view;
    }
}