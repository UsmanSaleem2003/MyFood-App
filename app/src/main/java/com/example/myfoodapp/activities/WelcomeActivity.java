package com.example.myfoodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();
    }






    public void register(View view) {
        startActivity(new Intent(WelcomeActivity.this, RegisterationActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }


}