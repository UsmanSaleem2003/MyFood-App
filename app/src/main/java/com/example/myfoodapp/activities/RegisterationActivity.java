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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
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

        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.editText);
        emailEditText = findViewById(R.id.editText2);
        passwordEditText = findViewById(R.id.editText3);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Creating your account...");

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
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("fullName", name);
                        userMap.put("email", email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(uid)
                                .setValue(userMap)
                                .addOnCompleteListener(task1 -> {
                                    progressDialog.dismiss();
                                    if (task1.isSuccessful()) {
                                        if (!RegisterationActivity.this.isFinishing()) {
                                            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(this, "Database error: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    public void login(View view) {
        startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
    }
}
