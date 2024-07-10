package com.project.moviebuddy.ui.login;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.data.local.helpers.UserHelper;
import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class LoginViewModel extends ViewModel {
    private final UserHelper userHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public LoginViewModel(
            UserHelper userHelper,
            SharedPreferencesManager preferencesManager,
            Context context
    ) {
        this.userHelper = userHelper;
        this.preferencesManager = preferencesManager;
        this.context = context;

        userHelper.open();
    }

    public boolean loginUser(String username, String password) {
        Cursor cursor = userHelper.loginUser(username, password);
        int userID = -1;
        String email = SharedPreferencesManager.PREFERENCE_DEFAULT_VALUE;
        boolean loginResponse = false;

        try {
            if (cursor.moveToFirst()) {
                String usernameRetrieved = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseContract.UserColumns.USERNAME
                        )
                );

                String passwordRetrieved = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseContract.UserColumns.PASSWORD
                        )
                );

                userID = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseContract.UserColumns._ID
                        )
                );

                email = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseContract.UserColumns.EMAIL
                        )
                );

                loginResponse = usernameRetrieved.equals(username) && passwordRetrieved.equals(password);
            }

            if (loginResponse) {
                User user = new User();
                user.setUserID(userID);
                user.setUsername(username);
                user.setEmail(email);

                preferencesManager.saveUser(context, user);
            }
        } catch (Exception e) {
            android.util.Log.e("FTEST", "login error = " + e.getLocalizedMessage());
            loginResponse = false;
        }

        return loginResponse;
    }
}
