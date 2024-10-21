package com.example.myfitnesstracker;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView profileName;
    private TextView profileDOB;
    private TextView profileHeight;
    private TextView profileWeight;
    private TextView profileGoal;
    private TextView profileBodyType;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        profileName = view.findViewById(R.id.profileName);
        profileDOB = view.findViewById(R.id.profileDOB);
        profileHeight = view.findViewById(R.id.profileHeight);
        profileWeight = view.findViewById(R.id.profileWeight);
        profileGoal = view.findViewById(R.id.profileGoal);
        profileBodyType = view.findViewById(R.id.profileBodyType);
        dbHelper = new DBHelper(getActivity());

        // Fetch user profile data from the database
        loadUserProfile();

        return view;
    }

    private void loadUserProfile() {
        // Retrieve logged-in user's ID from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyFitnessTrackerPrefs", getActivity().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // -1 is the default value if not found

        if (userId == -1) {
            // Handle case where user is not logged in
            Log.d("ProfileFragment", "User not logged in");
            profileName.setText("No user logged in");
            return;
        } else {
            // Proceed with logged-in user
            Log.d("ProfileFragment", "Logged in userId: " + userId);
        }

        // Fetch user profile data based on user ID
        Cursor cursor = dbHelper.getProfileDataForUser(userId); // Update DBHelper to fetch profile for specific user

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex("dob"));
            @SuppressLint("Range") String height = cursor.getString(cursor.getColumnIndex("height"));
            @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex("weight"));
            @SuppressLint("Range") String goal = cursor.getString(cursor.getColumnIndex("goal"));
            @SuppressLint("Range") String bodyType = cursor.getString(cursor.getColumnIndex("body_type"));

            // Set the TextViews with user data
            profileName.setText("Name: " + name);
            profileDOB.setText("Date of Birth: " + dob);
            profileHeight.setText("Height: " + height);
            profileWeight.setText("Weight: " + weight);
            profileGoal.setText("Goal: " + goal);
            profileBodyType.setText("Body Type: " + bodyType);
        }

        cursor.close();
    }
}
