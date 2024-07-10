package com.project.moviebuddy.ui.main.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class AccountViewModel extends ViewModel {
    private SharedPreferencesManager preferencesManager;
    private final Context context;

    public AccountViewModel(
            SharedPreferencesManager sharedPreferencesManager,
            Context context
    ) {
        preferencesManager = sharedPreferencesManager;
        this.context = context;

        android.util.Log.e("FTEST", "terpanggil");
    }

    public User getUserData() {
        return preferencesManager.getSavedUser(context);
    }

    public void logout() {
        preferencesManager.clearAllPreferences(context);
    }
}