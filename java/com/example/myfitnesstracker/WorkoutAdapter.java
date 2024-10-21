package com.example.myfitnesstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<Exercise> exerciseList;
    private FragmentActivity activity;

    public WorkoutAdapter(FragmentActivity activity, List<Exercise> exerciseList) {
        this.activity = activity;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getName());
        holder.exerciseImage.setImageResource(exercise.getImageResId());

        // Handle click on each exercise item
        holder.itemView.setOnClickListener(v -> {
            // Create a bundle to pass the exercise details
            Bundle bundle = new Bundle();
            bundle.putString("name", exercise.getName());
            bundle.putInt("imageResId", exercise.getImageResId());
            bundle.putString("description", exercise.getSteps());



            // Navigate to ExerciseDetailFragment with the bundle
            ExerciseDetailFragment detailFragment = new ExerciseDetailFragment();
            detailFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)  // Make sure fragment_container is the ID where fragments are hosted
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        ImageView exerciseImage;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
        }
    }
}
