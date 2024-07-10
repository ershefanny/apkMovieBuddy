package com.project.moviebuddy.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.UserHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final UserHelper userHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    private static volatile LoginViewModelFactory INSTANCE;

    private LoginViewModelFactory(Context context) {
        this.userHelper = Injection.getUserHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(userHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static LoginViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LoginViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}