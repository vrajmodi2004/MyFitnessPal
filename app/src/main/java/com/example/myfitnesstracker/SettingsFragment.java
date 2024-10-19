package com.example.myfitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingsFragment extends Fragment {

    private Button feedbackButton;
    private Button logoutButton;
    private Button weeklyScheduleButton; // Add this line

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        feedbackButton = view.findViewById(R.id.button_feedback);
        logoutButton = view.findViewById(R.id.button_logout);
        weeklyScheduleButton = view.findViewById(R.id.button_weekly_schedule); // Initialize the new button

        // Set up click listeners
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackFragment();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Set up listener for the weekly schedule button
        weeklyScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeeklyScheduleActivity(); // Open the new activity
            }
        });

        return view;
    }

    private void openFeedbackFragment() {
        // Open the FeedbackFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new FeedbackFragment());
        transaction.addToBackStack(null); // Optional: Add to backstack to allow navigation back
        transaction.commit();
    }

    private void logout() {
        // Clear completed exercises using ProgressManager
        ProgressManager.getInstance(requireActivity()).clearCompletedExercises(); // Clear progress

        // Log the logout action
        Log.d("SettingsFragment", "Progress cleared on logout");

        // Redirect to login activity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish(); // Optional: Close the current activity
    }

    // Method to open the weekly schedule activity
    private void openWeeklyScheduleActivity() {
        Intent intent = new Intent(getActivity(), WeeklyScheduleActivity.class);
        startActivity(intent);
    }
}
