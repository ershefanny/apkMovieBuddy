package com.project.moviebuddy.ui.main.ui.profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.splash.SplashViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class AccountViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SharedPreferencesManager preferencesManager;
    private Context context;

    private static volatile AccountViewModelFactory INSTANCE;

    private AccountViewModelFactory(Context context) {
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AccountViewModel.class)) {
            return (T) new AccountViewModel(preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static AccountViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AccountViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}