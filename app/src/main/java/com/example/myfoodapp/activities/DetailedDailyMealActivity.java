package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.DetailedDailyAdapter;
import com.example.myfoodapp.models.DetailedDailyModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedDailyMealActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    List<DetailedDailyModel> detailedDailyModelList;
    DetailedDailyAdapter dailyAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_daily_meal);

        String type = getIntent().getStringExtra("type");


        recyclerView = findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.detailed_img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedDailyModelList = new ArrayList<>();

        dailyAdapter = new DetailedDailyAdapter(detailedDailyModelList);
        recyclerView.setAdapter(dailyAdapter);

        if(type != null && type.equalsIgnoreCase("breakfast"))
        {
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav1,"Breakfast","description","4.4","40","10 to 9"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav2,"Breakfast","description","4.4","40","10 to 9"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fav3,"Breakfast","description","4.4","40","10 to 9"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type != null && type.equalsIgnoreCase("lunch"))
        {
            imageView.setImageResource(R.drawable.lunch);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.sandwich1,"Lunch","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.sandwich2,"Lunch","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.sandwich3,"Lunch","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.sandwich4,"Lunch","description","4.4","40","10 am to 9 pm"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type != null && type.equalsIgnoreCase("dinner"))
        {
            imageView.setImageResource(R.drawable.dinner);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.pizza1,"Dinner","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.burger2,"Dinner","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.pizza3,"Dinner","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.burger3,"Dinner","description","4.4","40","10 am to 9 pm"));
            dailyAdapter.notifyDataSetChanged();
        }
        if(type != null && type.equalsIgnoreCase("coffe"))
        {
            imageView.setImageResource(R.drawable.coffe);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.coffe,"Coffee","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.fries1,"Fries","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.icecream2,"Ice Cream","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.coffe,"Coffee","description","4.4","40","10 am to 9 pm"));
            dailyAdapter.notifyDataSetChanged();
        }

        if(type != null && type.equalsIgnoreCase("sweets"))
        {
            imageView.setImageResource(R.drawable.sweets);
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s1,"Sweet","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s2,"Sweet","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s3,"Sweet","description","4.4","40","10 am to 9 pm"));
            detailedDailyModelList.add(new DetailedDailyModel(R.drawable.s4,"Sweet","description","4.4","40","10 am to 9 pm"));
            dailyAdapter.notifyDataSetChanged();
        }

    }
}