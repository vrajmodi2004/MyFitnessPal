package com.example.myfitnesstracker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutDB";
    private static final int DATABASE_VERSION = 3; // Incremented version for new columns

    // User table and column names
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Profile table and column names
    private static final String TABLE_PROFILE = "profile";
    private static final String COLUMN_PROFILE_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_BODY_TYPE = "body_type"; // New column for body type
    private static final String COLUMN_GOAL = "goal"; // New column for goal
    private static final String COLUMN_USER_ID_FK = "user_id"; // Foreign key to link user table

    // SQL statement to create the user table
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL);";

    // SQL statement to create the profile table
    private static final String TABLE_CREATE_PROFILE =
            "CREATE TABLE " + TABLE_PROFILE + " (" +
                    COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_DOB + " TEXT NOT NULL, " +
                    COLUMN_GENDER + " TEXT NOT NULL, " +
                    COLUMN_HEIGHT + " REAL NOT NULL, " +
                    COLUMN_WEIGHT + " REAL NOT NULL, " +
                    COLUMN_BODY_TYPE + " TEXT NOT NULL, " +
                    COLUMN_GOAL + " TEXT NOT NULL, " +
                    COLUMN_USER_ID_FK + " INTEGER, " +  // Add foreign key
                    "FOREIGN KEY (" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_PROFILE); // Create the profile table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE); // Drop the profile table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER); // Drop the user table
        onCreate(db);
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password); // Consider hashing passwords in a real app

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1; // Return true if user was registered successfully
    }

    // Method to check user credentials during login
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Case-insensitive username check, but password remains case-sensitive
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID},
                "LOWER(" + COLUMN_USERNAME + ")=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username.toLowerCase(), password}, null, null, null);

        boolean isValidUser = cursor.getCount() > 0; // User exists if count > 0
        cursor.close();
        db.close();
        return isValidUser;
    }

    // Method to save user profile data
    public boolean saveProfile(String name, String dob, String gender, String height, String weight, String bodyType, String goal, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_BODY_TYPE, bodyType);
        values.put(COLUMN_GOAL, goal);
        values.put(COLUMN_USER_ID_FK, userId); // Link profile with the user ID

        long result = db.insert(TABLE_PROFILE, null, values);
        db.close();
        return result != -1; // Return true if inserted successfully
    }


    // Method to get profile data based on user ID
    public Cursor getProfileDataForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + COLUMN_USER_ID_FK + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }


    // Method to get the user's goal based on the username
    // Method to get the user ID based on the username
    @SuppressLint("Range")
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch the user ID based on the username (case-insensitive)
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID},
                "LOWER(" + COLUMN_USERNAME + ")=?", new String[]{username.toLowerCase()},
                null, null, null);

        int userId = -1; // Default value if user is not found

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)); // Retrieve the user ID from the cursor
        }

        cursor.close();
        //db.close();
        Log.d("ProfileActivity", "Sent userid: "+userId);
        return userId; // Return the user ID or -1 if not found
    }

    // Method to get the user's goal based on the username
    @SuppressLint("Range")
    public String getUserGoal(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the user ID based on the username
        int userId = getUserId(username);
        if (userId == -1) {
            // If the userId is not found, return null or handle the error appropriately
            Log.d("getUserGoal","got invalid userId from this method");
            return null;
        }

        // Query to fetch the user's goal from the profile table using the user ID
        Cursor cursor = db.query(TABLE_PROFILE, new String[]{COLUMN_GOAL},
                COLUMN_USER_ID_FK + "=?", new String[]{String.valueOf(userId)},
                null, null, null);

        String userGoal = null;
        if (cursor.moveToFirst()) {
            // Retrieve the user's goal from the cursor
            userGoal = cursor.getString(cursor.getColumnIndex(COLUMN_GOAL));
        }

        cursor.close();
       // db.close();

        return userGoal; // Return the goal or null if not found
    }




}
