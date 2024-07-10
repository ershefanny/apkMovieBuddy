package com.project.moviebuddy.ui.register;

import android.content.ContentValues;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.UserHelper;

public class RegisterViewModel extends ViewModel {
    private final UserHelper userHelper;

    public RegisterViewModel(UserHelper userHelper) {
        this.userHelper = userHelper;
        userHelper.open();
    }

    public long registerUser(ContentValues values) {
        return userHelper.insert(values);
    }
}
