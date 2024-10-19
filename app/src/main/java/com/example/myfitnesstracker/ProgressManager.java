package com.example.myfitnesstracker;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class ProgressManager {
    private static ProgressManager instance;
    private SharedPreferences sharedPreferences;

    // Private constructor to prevent instantiation
    private ProgressManager(Context context) {
        sharedPreferences = context.getSharedPreferences("myFitnessTracker", Context.MODE_PRIVATE);
    }

    // Method to get the singleton instance
    public static synchronized ProgressManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProgressManager(context.getApplicationContext());
        }
        return instance;
    }

    // Method to clear completed exercises
    public void clearCompletedExercises() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("completedExercises"); // Remove completed exercises
        editor.apply(); // Commit the changes
    }

    // Method to add completed exercises (if needed)
    public void addCompletedExercise(String exercise) {
        Set<String> completedExercisesSet = sharedPreferences.getStringSet("completedExercises", new HashSet<>());
        completedExercisesSet.add(exercise); // Add new exercise
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("completedExercises", completedExercisesSet);
        editor.apply(); // Commit the changes
    }

    // You can add more methods to manage progress as needed
}
