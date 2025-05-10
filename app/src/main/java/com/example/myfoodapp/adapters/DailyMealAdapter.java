package com.example.myfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.DetailedDailyMealActivity;
import com.example.myfoodapp.models.DailyMealModel;

import java.util.List;

public class DailyMealAdapter extends RecyclerView.Adapter<DailyMealAdapter.ViewHolder> {

    Context context;
    List<DailyMealModel> list;

    public DailyMealAdapter(Context context, List<DailyMealModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DailyMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.discription.setText(list.get(position).getDiscription());
        holder.discount.setText(list.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedDailyMealActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,discount,discription;
            public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageview);
            name = itemView.findViewById(R.id.textView9);
            discount = itemView.findViewById(R.id.discount);
            discription = itemView.findViewById(R.id.textView10);

            }
    }
}
