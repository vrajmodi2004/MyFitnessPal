package com.example.myfitnesstracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etDOB, etHeight, etWeight;
    private Spinner spinnerGender, spinnerBodyType, spinnerGoal;
    private Button btnSubmitProfile;
    private DBHelper dbHelper;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        etName = findViewById(R.id.etName);
        etDOB = findViewById(R.id.etDOB);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerBodyType = findViewById(R.id.spinnerBodyType); // Added spinner for body type
        spinnerGoal = findViewById(R.id.spinnerGoal); // Added spinner for goal
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set up spinner for gender selection
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Set up spinner for body type selection
        ArrayAdapter<CharSequence> bodyTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.body_type_array, android.R.layout.simple_spinner_item);
        bodyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBodyType.setAdapter(bodyTypeAdapter);

        // Set up spinner for goal selection
        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(this,
                R.array.goal_array, android.R.layout.simple_spinner_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(goalAdapter);

        // Initialize calendar instance for DatePicker
        calendar = Calendar.getInstance();

        // Set click listener for Date of Birth EditText
        etDOB.setOnClickListener(view -> showDatePickerDialog());

        // Set click listener for submit button
        btnSubmitProfile.setOnClickListener(view -> saveProfile());
    }

    private void showDatePickerDialog() {
        // Get the current year, month, and day
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            // month is zero-based, so add 1
            String dob = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
            etDOB.setText(dob);
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String dob = etDOB.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String bodyType = spinnerBodyType.getSelectedItem().toString(); // Get selected body type
        String goal = spinnerGoal.getSelectedItem().toString(); // Get selected goal

        // Validate inputs
        if (name.isEmpty() || dob.isEmpty() || height.isEmpty() || weight.isEmpty() || bodyType.isEmpty() || goal.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve logged-in user's ID from shared preferences or intent

        int userid=dbHelper.getUserId(name);
        Log.d("ProfileActivity", "Saving profile: " + name + ", " + dob + ", " + gender + ", " + height + ", " + weight + ", " + bodyType + ", " + goal);
        // Save the profile data and associate it with the logged-in user
        boolean isSaved = dbHelper.saveProfile(name, dob, gender, height, weight, bodyType, goal,userid);
        SharedPreferences sharedPreferences = getSharedPreferences("MyFitnessTrackerPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", userid); // Assuming user_id is stored in SharedPreferences

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isSaved) {
            Toast.makeText(this, "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to Save Profile", Toast.LENGTH_SHORT).show();
        }
    }
}
