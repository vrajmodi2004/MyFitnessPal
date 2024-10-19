package com.example.myfitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private DBHelper dbHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Make sure this matches your layout file name

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.btnSignUp);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Attempt to register the user
        boolean isRegistered = dbHelper.registerUser(username, password);
        if (isRegistered) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            intent=new Intent(SignUpActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish(); // Close the sign-up activity and return to the previous one
        } else {
            Toast.makeText(this, "Registration Failed: Username may already exist", Toast.LENGTH_SHORT).show();
        }
    }
}
