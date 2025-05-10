package com.example.myfoodapp.adapters;

import android.content.Context;
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
import java.util.List;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {

    private BottomSheetDialog bottomSheetDialog;
    Context context;
    ArrayList<HomeVerModel> list;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new HomeVerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerAdapter.ViewHolder holder, int position)
    {

        final String mName = list.get(position).getName();
        final String mTiming = list.get(position).getTiming();
        final String mRating = list.get(position).getRating();
        final String mPrice = list.get(position).getPrice();
        final int mImage = list.get(position).getImage();

        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.timing.setText(list.get(position).getTiming());
        holder.rating.setText(list.get(position).getRating());
        holder.price.setText(list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetTheme);

                View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout,null);
                sheetView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Added To Cart",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                ImageView bottomImg = sheetView.findViewById(R.id.bottom_img);
                TextView bottomName = sheetView.findViewById(R.id.bottom_name);
                TextView bottomPrice = sheetView.findViewById(R.id.bottom_price);
                TextView bottomRating = sheetView.findViewById(R.id.bottom_rating);

                bottomName.setText(mName);
                bottomPrice.setText(mPrice);
                bottomRating.setText(mRating);
                bottomImg.setImageResource(mImage);

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,timing,rating,price;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.ver_img);
            name = itemView.findViewById(R.id.name);
            timing = itemView.findViewById(R.id.timing);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
        }
    }
}
