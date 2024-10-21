package com.example.myfitnesstracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgressFragment extends Fragment {

    private RecyclerView completedWorkoutsRecyclerView;
    private ProgressAdapter progressAdapter;
    private TextView noProgressTextView;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        // Initialize RecyclerView and noProgressTextView
        completedWorkoutsRecyclerView = view.findViewById(R.id.completedWorkoutsRecyclerView);
        noProgressTextView = view.findViewById(R.id.noProgressTextView);

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("myFitnessTracker", Context.MODE_PRIVATE);

        // Get the list of completed exercises from SharedPreferences
        Set<String> completedExercisesSet = sharedPreferences.getStringSet("completedExercises", new HashSet<>());
        List<String> completedExercises = new ArrayList<>(completedExercisesSet);

        // Set up RecyclerView if there are completed exercises, else show "No Progress" text
        if (completedExercises.isEmpty()) {
            noProgressTextView.setVisibility(View.VISIBLE);
            completedWorkoutsRecyclerView.setVisibility(View.GONE);
        } else {
            noProgressTextView.setVisibility(View.GONE);
            completedWorkoutsRecyclerView.setVisibility(View.VISIBLE);
            completedWorkoutsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            progressAdapter = new ProgressAdapter(completedExercises);
            completedWorkoutsRecyclerView.setAdapter(progressAdapter);
        }

        return view;
    }

    // Call this method when the user logs out
    public void clearProgressOnLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("completedExercises");
        editor.apply();
        Log.d("ProgressFragment", "SharedPreferences cleared for completedExercises");
    }

    // Method to add the ProgressFragment to the FragmentManager
    public static void addToFragmentManager(FragmentManager fragmentManager) {
        ProgressFragment progressFragment = new ProgressFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, progressFragment, "progressFragmentTag");
        transaction.commit();
    }
}
