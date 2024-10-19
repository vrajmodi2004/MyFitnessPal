package com.example.myfitnesstracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {

    private EditText feedbackEditText;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedbackEditText = view.findViewById(R.id.edittext_feedback);
        submitButton = view.findViewById(R.id.button_submit_feedback);

        // Set up click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {
        String feedback = feedbackEditText.getText().toString().trim();

        if (TextUtils.isEmpty(feedback)) {
            // Show error if feedback is empty
            Toast.makeText(getActivity(), "Please enter your feedback", Toast.LENGTH_SHORT).show();
        } else {
            // Logic to handle the feedback submission
            // You could save this feedback to a database, send it to a server, etc.

            // Show success toast
            Toast.makeText(getActivity(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();

            // Clear the input field
            feedbackEditText.setText("");
        }
    }
}
