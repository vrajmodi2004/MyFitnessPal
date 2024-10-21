package com.example.myfitnesstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private List<String> completedExercises;

    public ProgressAdapter(List<String> completedExercises) {
        this.completedExercises = completedExercises;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_completed_workout, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        String exercise = completedExercises.get(position);
        holder.completedWorkoutTextView.setText(exercise);
    }

    @Override
    public int getItemCount() {
        return completedExercises.size();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        TextView completedWorkoutTextView;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            completedWorkoutTextView = itemView.findViewById(R.id.completedWorkoutTextView);
        }
    }
}
