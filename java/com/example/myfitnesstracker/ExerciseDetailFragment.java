package com.example.myfitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ExerciseDetailFragment extends Fragment {

    private TextView exerciseName;
    private ImageView exerciseImage;
    private TextView exerciseDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_detail, container, false);

        // Initialize views
        exerciseName = view.findViewById(R.id.exerciseName);
        exerciseImage = view.findViewById(R.id.exerciseImage);
        exerciseDescription = view.findViewById(R.id.exerciseDescription);

        // Get exercise details from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            int imageResId = bundle.getInt("imageResId");
            String description = bundle.getString("description");

            // Set the details in the UI
            exerciseName.setText(name);
            exerciseImage.setImageResource(imageResId);
            exerciseDescription.setText(description);
        }

        return view;
    }
}
