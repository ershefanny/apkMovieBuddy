package com.project.moviebuddy.ui.splash;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.utils.SharedPreferencesManager;

public class SplashViewModel extends ViewModel {
    private SharedPreferencesManager preferencesManager;
    private final Context context;

    public SplashViewModel(
            SharedPreferencesManager sharedPreferencesManager,
            Context context
    ) {
        preferencesManager = sharedPreferencesManager;
        this.context = context;
    }

    public boolean isUserLoggedIn() {
        int userId = preferencesManager.getUserID(context);
        return userId != -1;
    }
}
