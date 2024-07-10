package com.project.moviebuddy.ui.splash;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class SplashViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SharedPreferencesManager preferencesManager;
    private Context context;

    private static volatile SplashViewModelFactory INSTANCE;

    private SplashViewModelFactory(Context context) {
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static SplashViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SplashViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SplashViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}