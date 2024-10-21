package com.example.myfitnesstracker;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {

    private TextView exerciseNameTextView, exerciseStepsTextView;
    private ImageView exerciseImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Initialize views
        exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        exerciseStepsTextView = findViewById(R.id.exerciseStepsTextView);
        exerciseImageView = findViewById(R.id.exerciseImageView);

        // Get the data from the intent
        String exerciseName = getIntent().getStringExtra("exercise_name");
        String exerciseSteps = getIntent().getStringExtra("exercise_steps");
        int exerciseImage = getIntent().getIntExtra("exercise_image", R.drawable.crunches); // Set a default image

        // Set data to views
        exerciseNameTextView.setText(exerciseName);
        exerciseStepsTextView.setText(exerciseSteps);
        exerciseImageView.setImageResource(exerciseImage);
    }
}
