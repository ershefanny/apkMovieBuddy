package com.project.moviebuddy.ui.main.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.myfilm.MyFilmViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    private static volatile HomeViewModelFactory INSTANCE;

    private HomeViewModelFactory(Context context) {
        this.filmHelper = Injection.getFilmHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(filmHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static HomeViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (HomeViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HomeViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}