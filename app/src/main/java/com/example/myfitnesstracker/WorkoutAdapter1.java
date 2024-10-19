package com.example.myfitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter1 extends RecyclerView.Adapter<WorkoutAdapter1.WorkoutViewHolder> {

    private List<Exercise> workoutList;
    private Context context;
    private OnExerciseClickListener onExerciseClickListener; // Add this

    // Add a constructor with the listener
    public WorkoutAdapter1(List<Exercise> workoutList, Context context, OnExerciseClickListener onExerciseClickListener) {
        this.workoutList = workoutList;
        this.context = context;
        this.onExerciseClickListener = onExerciseClickListener; // Initialize the listener
    }

    // Define the interface
    public interface OnExerciseClickListener {
        void onExerciseClick(String exerciseName);
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Exercise exercise = workoutList.get(position);
        holder.workoutNameTextView.setText(exercise.getName());

        holder.workoutImageView.setImageResource(exercise.getImageResId());

        // Set a click listener to save completed exercise and open ExerciseDetailActivity
        holder.itemView.setOnClickListener(v -> {
            // Call the onExerciseClick method from the listener
            onExerciseClickListener.onExerciseClick(exercise.getName());

            // Start the ExerciseDetailActivity
            Intent intent = new Intent(context, ExerciseDetailActivity.class);
            intent.putExtra("exercise_name", exercise.getName());
            intent.putExtra("exercise_steps", exercise.getSteps());
            intent.putExtra("exercise_image", exercise.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutNameTextView;
        public ImageView workoutImageView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.exerciseName);
            workoutImageView = itemView.findViewById(R.id.exerciseImage);
        }
    }
}
