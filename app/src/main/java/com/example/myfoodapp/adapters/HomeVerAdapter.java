package com.example.myfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.ItemDetailActivity;
import com.example.myfoodapp.models.HomeVerModel;

import java.util.ArrayList;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<HomeVerModel> list;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> list) {
        this.context = context;
        this.list = new ArrayList<>(list);
    }

    public void updateList(ArrayList<HomeVerModel> newList) {
        this.list = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeVerModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.timing.setText(model.getTiming());
        holder.rating.setText(String.valueOf(model.getRating()));
        holder.price.setText(String.format("$%.2f", model.getPrice()));

        String imageName = model.getImageName();
        final String cleanImageName;

        if (imageName != null && !imageName.trim().isEmpty()) {
            cleanImageName = imageName.trim().toLowerCase();

            int imageResId = context.getResources().getIdentifier(
                    cleanImageName,
                    "drawable",
                    context.getPackageName());

            if (imageResId != 0) {
                holder.imageView.setImageResource(imageResId);
            } else {
                Log.w("ImageLoad", "Drawable not found for: " + cleanImageName);
                holder.imageView.setImageResource(R.drawable.placeholder);
            }
        } else {
            cleanImageName = "";
            holder.imageView.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("product_id", model.getId());
            intent.putExtra("name", model.getName());
            intent.putExtra("price", model.getPrice());
            intent.putExtra("rating", model.getRating());
            intent.putExtra("timing", model.getTiming());
            intent.putExtra("image", cleanImageName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, timing, rating, price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timing = itemView.findViewById(R.id.timing);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.ver_img);
        }
    }
}
