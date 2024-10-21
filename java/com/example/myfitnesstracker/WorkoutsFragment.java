package com.example.myfitnesstracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkoutsFragment extends Fragment implements WorkoutAdapter1.OnExerciseClickListener{

    private TextView welcomeMessage;
    private RecyclerView recommendedWorkoutsRecyclerView;
    private DBHelper dbHelper;
    private String username;
    private String goal;

    @Override
    public void onExerciseClick(String exerciseName) {
        saveCompletedExercise(exerciseName); // Call saveCompletedExercise
    }

    private void saveCompletedExercise(String exerciseName) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myFitnessTracker", Context.MODE_PRIVATE);
        Set<String> completedExercises = sharedPreferences.getStringSet("completedExercises", new HashSet<>());

        completedExercises.add(exerciseName);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("completedExercises", completedExercises);
        editor.apply();
    }


    @SuppressLint("Range")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        // Initialize views
        welcomeMessage = view.findViewById(R.id.welcomeMessage);
        recommendedWorkoutsRecyclerView = view.findViewById(R.id.recommendedWorkoutsRecyclerView);
        dbHelper = new DBHelper(getActivity());
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        welcomeMessage.setText("Welcome, "+username.toUpperCase());

        goal=dbHelper.getUserGoal(username);

        List<Exercise> recommendedWorkouts = getRecommendedWorkouts(goal);

        // Set up RecyclerView
        recommendedWorkoutsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        WorkoutAdapter1 adapter = new WorkoutAdapter1(recommendedWorkouts,getActivity(),this);
        recommendedWorkoutsRecyclerView.setAdapter(adapter);


        return view;
    }

    private List<Exercise> getRecommendedWorkouts(String goal) {
        List<Exercise> workouts = new ArrayList<>();

        if (goal.equalsIgnoreCase("Muscle Gain")) {
            workouts.add(new Exercise("Push-Ups", "Muscle Group: Chest, Shoulders, Triceps, Core.\n" +
                    "Step 1: Start in a plank position with your hands shoulder-width apart.\n" +
                    "Step 2: Lower your body by bending your elbows, keeping your body in a straight line.\n" +
                    "Step 3: Push back up to the starting position.\n" +
                    "Step 4: Perform multiple sets and progressively increase reps to build upper body muscle.", R.drawable.pushup));
            workouts.add(new Exercise("Pull-Ups(using a pull-up bar or a sturdy ledge)", "Muscle Group: Back, Biceps, Shoulders.\n" +
                    "Step 1: Grab the pull-up bar with your palms facing forward (overhand grip) or towards you (underhand grip for chin-ups).\n" +
                    "Step 2: Hang from the bar with arms fully extended.\n" +
                    "Step 3: Pull your body up until your chin passes the bar, keeping your core engaged.\n" +
                    "Step 4: Lower back down and repeat for multiple reps, increasing sets as you progress.", R.drawable.pullup));
            workouts.add(new Exercise("Dumbell Rows (or bodyweight rows using a table)", "Muscle Group: Back, Shoulders, Biceps.\n" +
                    "Step 1: If using dumbbells, bend at the hips with a slight bend in your knees.\n" +
                    "Step 2: Pull the dumbbells towards your body, squeezing your shoulder blades together.\n" +
                    "Step 3: Lower the weights back down and repeat for multiple reps.\n" +
                    "Step 4: If using a table, lie underneath it, grip the edge, and pull yourself up toward the surface.", R.drawable.dumbellrow));
            workouts.add(new Exercise("Pike Push-Ups", "Muscle Group: Shoulders, Triceps, Upper Chest.\n" +
                    "Step 1: Start in a downward dog position with your hips raised and hands on the floor.\n" +
                    "Step 2: Bend your elbows and lower your head toward the floor.\n" +
                    "Step 3: Push back up to the starting position.\n" +
                    "Step 4: Perform multiple sets to target the shoulder muscles.", R.drawable.pikepushup));
            workouts.add(new Exercise("Dips(using a chair or a sturdy surface)", "Muscle Group: Triceps, Chest, Shoulders.\n" +
                    "Step 1: Sit on the edge of a sturdy chair and place your hands on the edge.\n" +
                    "Step 2: Slide your hips off the chair and lower your body by bending your elbows.\n" +
                    "Step 3: Push back up to straighten your arms.\n" +
                    "Step 4: Repeat for multiple reps, and add weight as you progress.", R.drawable.dipsusingchair));
            workouts.add(new Exercise("Single-Leg Deadlift (using dumbbells or bodyweight)", "Muscle Group: Hamstrings, Glutes, Core.\n" +
                    "Step 1: Stand on one leg, holding dumbbells or with your arms by your side.\n" +
                    "Step 2: Hinge at the hips and lower your torso toward the ground, extending your opposite leg behind you.\n" +
                    "Step 3: Return to the starting position by squeezing your glutes and pushing through the heel of the supporting leg.\n" +
                    "Step 4: Repeat for multiple reps and switch legs.", R.drawable.singlelegdeadlift));
            workouts.add(new Exercise("Step-Ups (using a chair or sturdy surface)", "Muscle Group: Quads, Glutes, Hamstrings, Calves.\n" +
                    "Step 1: Place one foot on a chair or elevated surface.\n" +
                    "Step 2: Push through the heel of the raised leg to lift your body up.\n" +
                    "Step 3: Step down with control and switch legs.\n" +
                    "Step 4: Perform for multiple reps to strengthen your legs and glutes.", R.drawable.stepups));
            workouts.add(new Exercise("Calf Raises", "Muscle Group: Calves.\n" +
                    "Step 1: Stand with your feet hip-width apart.\n" +
                    "Step 2: Slowly rise onto your toes, lifting your heels off the ground.\n" +
                    "Step 3: Lower back down and repeat for multiple reps.\n" +
                    "Step 4: Increase the intensity by holding dumbbells or a backpack with weights.", R.drawable.calfraises));
            workouts.add(new Exercise("Towel Rows (using a towel and door frame)", "Muscle Group: Back, Biceps.\n" +
                    "Step 1: Loop a towel through a sturdy door handle or frame.\n" +
                    "Step 2: Hold onto both ends of the towel and lean back with your body straight.\n" +
                    "Step 3: Pull your body toward the door by squeezing your back muscles and bending your elbows.\n" +
                    "Step 4: Lower back down and repeat for multiple reps.", R.drawable.towelraises));
        } else if (goal.equalsIgnoreCase("Weight Loss")) {
            workouts.add(new Exercise("Jump Rope", "Muscle Group: Full body, focusing on calves, shoulders, and core.\n" +
                    "Step 1: Stand with feet together, holding a jump rope handle in each hand.\n" +
                    "Step 2: Swing the rope over your head and jump over it as it reaches your feet.\n" +
                    "Step 3: Keep your core engaged and jump consistently for a set time.\n" +
                    "Step 4: Gradually increase the speed or time duration to burn more calories.", R.drawable.jumpingrope));
            workouts.add(new Exercise("Burpees", "Muscle Group: Full body (Legs, Chest, Shoulders, Core).\n" +
                    "Step 1: Stand with feet shoulder-width apart.\n" +
                    "Step 2: Drop into a squat, place your hands on the floor, and jump your feet back into a plank.\n" +
                    "Step 3: Perform a push-up, then jump your feet back toward your hands and explode upward into a jump.\n" +
                    "Step 4: Repeat the movement continuously for a set number of reps or time.", R.drawable.burpee));
            workouts.add(new Exercise("Jumping Jacks", "Muscle Group: Full body (Legs, Shoulders, Core).\n" +
                    "Step 1: Stand with feet together and arms at your sides.\n" +
                    "Step 2: Jump your feet out while raising your arms above your head.\n" +
                    "Step 3: Jump back to the starting position and repeat continuously.\n" +
                    "Step 4: Perform for a set time (e.g., 1-2 minutes) as part of a circuit workout.", R.drawable.jumpingjacks));
            workouts.add(new Exercise("Squat Jumps", "Muscle Group: Legs (Quadriceps, Glutes, Hamstrings).\n" +
                    "Step 1: Stand with feet shoulder-width apart.\n" +
                    "Step 2: Lower into a squat by bending your knees and pushing your hips back.\n" +
                    "Step 3: Explode upward into a jump, landing softly and immediately lowering into another squat.\n" +
                    "Step 4: Perform for a set number of reps to burn calories and build strength.", R.drawable.squatjumps));
            workouts.add(new Exercise("Plank-to-Pushups", "Muscle Group: Core, Shoulders, Chest, Triceps.\n" +
                    "Step 1: Start in a forearm plank position with elbows under your shoulders.\n" +
                    "Step 2: Push up onto your hands one arm at a time, transitioning into a high plank.\n" +
                    "Step 3: Lower back down to your forearms and repeat for multiple reps.\n" +
                    "Step 4: Continue for a set time to engage multiple muscle groups and burn fat.", R.drawable.planktopushup));
            workouts.add(new Exercise("Russian Twists", "Muscle Group: Core, Obliques.\n" +
                    "Step 1: Sit on the floor with knees bent and feet slightly lifted.\n" +
                    "Step 2: Lean back slightly and rotate your torso to one side, tapping the floor with your hands.\n" +
                    "Step 3: Twist to the opposite side and repeat for multiple reps.\n" +
                    "Step 4: Perform continuously for a set time to engage your core and obliques.", R.drawable.russiantwist));
            workouts.add(new Exercise("Stair Climbing", "Muscle Group: Legs (Quadriceps, Glutes, Calves).\n" +
                    "Step 1: Use a staircase at home and walk or run up the stairs as fast as possible.\n" +
                    "Step 2: Walk or jog back down for recovery.\n" +
                    "Step 3: Repeat the cycle for a set number of rounds to raise your heart rate and burn calories.", R.drawable.stairclimbing));
            workouts.add(new Exercise("Glute Bridge", "Muscle Group: Glutes, Hamstrings, Core.\n" +
                    "Step 1: Lie on your back with your knees bent and feet flat on the floor.\n" +
                    "Step 2: Lift your hips off the ground by engaging your glutes and core.\n" +
                    "Step 3: Hold the top position for a few seconds, then lower your hips back down.\n" +
                    "Step 4: Perform multiple reps to strengthen your glutes and hamstrings while burning calories.", R.drawable.glutebridge));
        } else if (goal.equalsIgnoreCase("Endurance")) {
            workouts.add(new Exercise("Jumping Jacks", "Muscle Group: Full body (Legs, Shoulders, Core)\n" +
                    "Step 1: Stand with feet together and arms at your sides.\n" +
                    "Step 2: Jump your feet out while raising your arms above your head.\n" +
                    "Step 3: Jump back to the starting position and repeat continuously for a set time.", R.drawable.jumpingjacks));
            workouts.add(new Exercise("Burpees", "Muscle Group: Full body (Legs, Chest, Shoulders, Core)\n" +
                    "Step 1: Stand with feet shoulder-width apart.\n" +
                    "Step 2: Drop into a squat, place your hands on the floor, and kick your legs back into a plank.\n" +
                    "Step 3: Perform a push-up, then jump your feet back toward your hands and explode upward into a jump.\n" +
                    "Step 4: Repeat for multiple reps to build endurance.", R.drawable.burpee));
            workouts.add(new Exercise("Mountain Climbers", "Muscle Group: Core, Shoulders, Triceps, Legs\n" +
                    "Step 1: Start in a high plank position with hands under your shoulders.\n" +
                    "Step 2: Bring one knee toward your chest, then quickly switch legs in a running motion.\n" +
                    "Step 3: Continue alternating legs at a fast pace to raise your heart rate and build endurance.", R.drawable.mountainclimbers));
            workouts.add(new Exercise("High Knees", "Muscle Group: Legs, Core\n" +
                    "Step 1: Stand with feet hip-width apart and arms at your sides.\n" +
                    "Step 2: Quickly raise one knee toward your chest while pumping the opposite arm.\n" +
                    "Step 3: Switch legs and continue alternating, as if running in place, while keeping your core engaged.", R.drawable.highknees));
            workouts.add(new Exercise("Jump Rope", "Muscle Group: Full body, focusing on calves, shoulders, and core\n" +
                    "Step 1: Hold a jump rope or mimic the motion with an imaginary rope.\n" +
                    "Step 2: Jump with both feet at a steady pace, swinging the rope overhead.\n" +
                    "Step 3: Gradually increase the speed or duration of the jumps.", R.drawable.jumpingrope));
            workouts.add(new Exercise("Squat Jumps", "Muscle Group: Quadriceps, Glutes, Hamstrings, Calves\n" +
                    "Step 1: Stand with feet shoulder-width apart.\n" +
                    "Step 2: Lower into a squat position by bending your knees and pushing your hips back.\n" +
                    "Step 3: Explode upward into a jump and land softly, returning to the squat position.\n" +
                    "Step 4: Repeat for multiple reps to build endurance in your legs.", R.drawable.squatjumps));
            workouts.add(new Exercise("Lunges", "Muscle Group: Quadriceps, Glutes, Hamstrings\n" +
                    "Step 1: Stand with feet hip-width apart.\n" +
                    "Step 2: Step one foot forward and lower your body until your front thigh is parallel to the ground.\n" +
                    "Step 3: Push back up to the starting position and switch legs.\n" +
                    "Step 4: Perform alternating lunges for a set number of reps.", R.drawable.lunges));
            workouts.add(new Exercise("Plank", "Muscle Group: Core, Shoulders\n" +
                    "Step 1: Get into a forearm plank position with your elbows directly under your shoulders.\n" +
                    "Step 2: Keep your body in a straight line from head to heels, engaging your core.\n" +
                    "Step 3: Hold this position for as long as possible to build endurance in your core.", R.drawable.plank));
            workouts.add(new Exercise("Wall Sits", "Muscle Group: Quadriceps, Glutes, Core\n" +
                    "Step 1: Stand with your back against a wall and feet about two feet away.\n" +
                    "Step 2: Slide down the wall until your knees are at a 90-degree angle, as if sitting in an invisible chair.\n" +
                    "Step 3: Hold this position for as long as possible, building endurance in your legs and core.", R.drawable.wallsits));
            workouts.add(new Exercise("Side-to-Side Shuffles", "Muscle Group: Legs, Core\n" +
                    "Step 1: Stand with feet shoulder-width apart and knees slightly bent.\n" +
                    "Step 2: Shuffle quickly to one side for a few steps, then shuffle back in the opposite direction.\n" +
                    "Step 3: Continue shuffling side-to-side to raise your heart rate and improve endurance.", R.drawable.sidetosideshuffles));
        } else {
            workouts.add(new Exercise("Push-Ups", "Muscle Group: Chest, Shoulders, Triceps, Core\n" +
                    "\n" +
                    "Step 1: Get into a high plank position with hands slightly wider than shoulder-width apart.\n" +
                    "Step 2: Lower your body until your chest almost touches the floor, keeping your body in a straight line.\n" +
                    "Step 3: Push back up to the starting position.\n", R.drawable.pushup));
            workouts.add(new Exercise("Body Weight Squats", "Muscle Group: Quadriceps, Glutes, Hamstrings\n" +
                    "\n" +
                    "Step 1: Stand with feet shoulder-width apart, toes slightly pointed out.\n" +
                    "Step 2: Lower your body by bending your knees and pushing your hips back as if sitting in a chair.\n" +
                    "Step 3: Go down until your thighs are parallel to the floor, then push back up through your heels.", R.drawable.bodyweightsquats));
            workouts.add(new Exercise("Lunges", "Muscle Group: Quadriceps, Glutes, Hamstrings\n" +
                    "\n" +
                    "Step 1: Stand with feet hip-width apart.\n" +
                    "Step 2: Step one leg forward and lower your body until your front thigh is parallel to the ground.\n" +
                    "Step 3: Push back up to the starting position and switch legs.", R.drawable.lunges));
            workouts.add(new Exercise("Plank", "Muscle Group: Core, Shoulders\n" +
                    "\n" +
                    "Step 1: Get into a forearm plank position with elbows directly under shoulders.\n" +
                    "Step 2: Keep your body in a straight line from head to heels.\n" +
                    "Step 3: Hold for as long as you can while maintaining form.", R.drawable.plank));
            workouts.add(new Exercise("Dips using a chair", "Muscle Group: Triceps, Shoulders, Chest\n" +
                    "\n" +
                    "Step 1: Sit on the edge of a chair or bench, hands beside your hips.\n" +
                    "Step 2: Slide off the chair and lower your body by bending your elbows.\n" +
                    "Step 3: Push through your palms to lift yourself back up.", R.drawable.dipsusingchair));
            workouts.add(new Exercise("Glute Bridges", "Muscle Group: Glutes, Hamstrings\n" +
                    "\n" +
                    "Step 1: Lie on your back with your knees bent and feet flat on the ground.\n" +
                    "Step 2: Lift your hips up until your body forms a straight line from shoulders to knees.\n" +
                    "Step 3: Squeeze your glutes at the top, then lower back down.", R.drawable.glutebridge));
            workouts.add(new Exercise("Wall Sits", "Muscle Group: Quadriceps, Glutes, Core\n" +
                    "\n" +
                    "Step 1: Stand with your back against a wall and feet about two feet away.\n" +
                    "Step 2: Slide down until your knees are at a 90-degree angle.\n" +
                    "Step 3: Hold this position as long as possible.", R.drawable.wallsits));
            workouts.add(new Exercise("Mountain Climbers", "Muscle Group: Core, Shoulders, Triceps\n" +
                    "\n" +
                    "Step 1: Get into a high plank position with hands under shoulders.\n" +
                    "Step 2: Drive one knee toward your chest, then quickly switch legs.\n" +
                    "Step 3: Keep alternating legs as fast as possible.", R.drawable.mountainclimbers));
            workouts.add(new Exercise("Burpees", "Muscle Group: Full body (Chest, Shoulders, Legs, Core)\n" +
                    "\n" +
                    "Step 1: Start in a standing position.\n" +
                    "Step 2: Drop into a squat, kick your legs back into a plank position.\n" +
                    "Step 3: Perform a push-up, then jump back into a squat and explode upward with a jump.", R.drawable.burpee));
            workouts.add(new Exercise("Step-Ups(Using a Chair or Stairs)", "Muscle Group: Quadriceps, Glutes, Hamstrings\n" +
                    "\n" +
                    "Step 1: Stand in front of a sturdy chair or step.\n" +
                    "Step 2: Step one foot onto the chair, pressing through your heel to lift your body up.\n" +
                    "Step 3: Lower back down and repeat on the other side.", R.drawable.stepups));
            workouts.add(new Exercise("Reverse Crunches", "Muscle Group: Core, Lower Abs\n" +
                    "\n" +
                    "Step 1: Lie on your back with your legs bent and feet off the floor.\n" +
                    "Step 2: Lift your hips off the ground by curling your knees toward your chest.\n" +
                    "Step 3: Slowly lower your hips back down and repeat.", R.drawable.reversecrunch));

        }

        return workouts;
    }
}
