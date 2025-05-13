package com.example.myfoodapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.models.HomeVerModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
        holder.rating.setText(model.getRating());
        holder.price.setText(model.getPrice());

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
            cleanImageName = "";  // fallback to placeholder in dialog too
            holder.imageView.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
            View sheet = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
            dialog.setContentView(sheet);

            ImageView bottomImg = sheet.findViewById(R.id.bottom_img);
            TextView bottomName = sheet.findViewById(R.id.bottom_name);
            TextView bottomPrice = sheet.findViewById(R.id.bottom_price);
            TextView bottomRating = sheet.findViewById(R.id.bottom_rating);
            TextView bottomTiming = sheet.findViewById(R.id.bottom_timing);

            bottomName.setText(model.getName());
            bottomPrice.setText(model.getPrice());
            bottomRating.setText(model.getRating());
            bottomTiming.setText(model.getTiming());

            int bottomResId = context.getResources().getIdentifier(
                    cleanImageName,
                    "drawable",
                    context.getPackageName());

            bottomImg.setImageResource(bottomResId != 0 ? bottomResId : R.drawable.placeholder);

            sheet.findViewById(R.id.add_to_cart).setOnClickListener(v -> {
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            dialog.show();
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