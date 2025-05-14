package com.example.myfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfoodapp.R;
import com.example.myfoodapp.models.CartItemModel;
import com.example.myfoodapp.utils.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<CartItemModel> cartItems;
    private final Runnable onCartChanged; // callback to update total

    public CartAdapter(Context context, List<CartItemModel> cartItems, Runnable onCartChanged) {
        this.context = context;
        this.cartItems = cartItems;
        this.onCartChanged = onCartChanged;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartItemModel model = cartItems.get(position);
        holder.name.setText(model.getName());
        holder.price.setText("$" + String.format("%.2f", model.getPrice()));
        holder.timing.setText(model.getTiming());
        holder.quantity.setText(String.valueOf(model.getQuantity()));

        int imageResId = context.getResources().getIdentifier(
                model.getImageName().toLowerCase().replace(".jpg", "").replace(".png", ""),
                "drawable", context.getPackageName());

        holder.image.setImageResource(imageResId != 0 ? imageResId : R.drawable.placeholder);

        holder.plusBtn.setOnClickListener(v -> {
            model.setQuantity(model.getQuantity() + 1);
            notifyItemChanged(position);
            onCartChanged.run();
        });

        holder.minusBtn.setOnClickListener(v -> {
            if (model.getQuantity() > 1) {
                model.setQuantity(model.getQuantity() - 1);
                notifyItemChanged(position);
            } else {
                cartItems.remove(position);
                notifyItemRemoved(position);
            }
            onCartChanged.run();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, timing, quantity;
        ImageView image;
        Button plusBtn, minusBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            timing = itemView.findViewById(R.id.cart_item_timing);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            image = itemView.findViewById(R.id.cart_item_img);
            plusBtn = itemView.findViewById(R.id.cart_item_plus);
            minusBtn = itemView.findViewById(R.id.cart_item_minus);
        }
    }
}
