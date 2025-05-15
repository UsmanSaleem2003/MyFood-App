package com.example.myfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.ItemDetailActivity;
import com.example.myfoodapp.models.HomeVerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<HomeVerModel> favorites;

    public FavoritesAdapter(Context context, ArrayList<HomeVerModel> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeVerModel model = favorites.get(position);

        holder.name.setText(model.getName());
        holder.timing.setText(model.getTiming());
        holder.rating.setText(String.valueOf(model.getRating()));
        holder.price.setText(String.format("$%.2f", model.getPrice()));

        int resId = context.getResources().getIdentifier(
                model.getImageName().replace(".jpg", "").replace(".png", ""),
                "drawable",
                context.getPackageName());

        holder.image.setImageResource(resId != 0 ? resId : R.drawable.placeholder);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ItemDetailActivity.class);
            i.putExtra("product_id", model.getId());
            context.startActivity(i);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("favorites")
                    .child(uid).child(String.valueOf(model.getId())).removeValue();

            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, timing, rating, price;
        ImageView image, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fav_name);
            timing = itemView.findViewById(R.id.fav_timing);
            rating = itemView.findViewById(R.id.fav_rating);
            price = itemView.findViewById(R.id.fav_price);
            image = itemView.findViewById(R.id.fav_img);
            deleteBtn = itemView.findViewById(R.id.fav_delete);
        }
    }
}
