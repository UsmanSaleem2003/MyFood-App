package com.example.myfoodapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.MainActivity;
import com.example.myfoodapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterationActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    EditText nameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeration);
//        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link EditText fields
        nameEditText = findViewById(R.id.editText);    // Full Name (not used in Firebase Auth, but useful for DB later)
        emailEditText = findViewById(R.id.editText2);  // Email
        passwordEditText = findViewById(R.id.editText3);  // Password

        // Setup progress dialog
        progressDialog = new ProgressDialog(RegisterationActivity.this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Creating your account...");

        // Button click listener
        findViewById(R.id.button).setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));  // Go to main screen
                        finish();
                    } else {
                        Toast.makeText(this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void login(View view) {
        startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
    }
}
