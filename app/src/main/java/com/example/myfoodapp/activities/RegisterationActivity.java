package com.example.myfoodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;

public class RegisterationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeration);

        getSupportActionBar().hide();
    }

    public void login(View view)
    {
        startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
    }

}