package com.project.moviebuddy.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.project.moviebuddy.data.model.User;

public class SharedPreferencesManager {
    private static final String SETTINGS_PREFERENCES = "settings_preferences";

    private static final String USERNAME_PREFERENCE = "username_preference";
    private static final String EMAIL_PREFERENCE = "email_preference";
    private static final String USER_ID_PREFERENCE = "user_id_preference";

    public static final String PREFERENCE_DEFAULT_VALUE = "preference_default_value";

    public void saveUser(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(USER_ID_PREFERENCE, user.getUserID());
        editor.putString(EMAIL_PREFERENCE, user.getEmail());
        editor.putString(USERNAME_PREFERENCE, user.getUsername());
        editor.apply();
    }

    public int getUserID(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(USER_ID_PREFERENCE, -1);
    }

    public User getSavedUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        int userID = sharedPref.getInt(USER_ID_PREFERENCE, -1);
        String username = sharedPref.getString(USERNAME_PREFERENCE, PREFERENCE_DEFAULT_VALUE);
        String email = sharedPref.getString(EMAIL_PREFERENCE, PREFERENCE_DEFAULT_VALUE);

        User user = new User();
        user.setUserID(userID);
        user.setUsername(username);
        user.setEmail(email);

        return user;
    }

    public void clearAllPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
    }
}