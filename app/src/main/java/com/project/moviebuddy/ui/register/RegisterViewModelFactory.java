package com.project.moviebuddy.ui.register;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.UserHelper;
import com.project.moviebuddy.di.Injection;

public class RegisterViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final UserHelper userHelper;

    private static volatile RegisterViewModelFactory INSTANCE;

    private RegisterViewModelFactory(Context context) {
        this.userHelper = Injection.getUserHelperInstance(context);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(userHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static RegisterViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RegisterViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RegisterViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}