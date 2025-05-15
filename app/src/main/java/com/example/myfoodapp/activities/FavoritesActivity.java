package com.example.myfoodapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.FavoritesAdapter;
import com.example.myfoodapp.models.HomeVerModel;
import com.example.myfoodapp.utils.VerticalSpacingItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private final ArrayList<HomeVerModel> favoriteList = new ArrayList<>();
    private DatabaseReference favRef;
    private TextView totalFavoritesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.favorites_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(this, favoriteList);
        recyclerView.setAdapter(adapter);

        totalFavoritesText = findViewById(R.id.total_favorites_text);
        recyclerView.addItemDecoration(new VerticalSpacingItemDecoration(24));


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favRef = FirebaseDatabase.getInstance().getReference("favorites").child(uid);

        loadFavorites();
    }

    private void loadFavorites() {
        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteList.clear();
                for (DataSnapshot itemSnap : snapshot.getChildren()) {
                    HomeVerModel model = itemSnap.getValue(HomeVerModel.class);
                    if (model != null) {
                        favoriteList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
                totalFavoritesText.setText("Total Favorites: " + favoriteList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FavoritesActivity.this, "Failed to load favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
