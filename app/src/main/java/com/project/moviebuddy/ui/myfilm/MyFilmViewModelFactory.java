package com.project.moviebuddy.ui.myfilm;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class MyFilmViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    private static volatile MyFilmViewModelFactory INSTANCE;

    private MyFilmViewModelFactory(Context context) {
        this.filmHelper = Injection.getFilmHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyFilmViewModel.class)) {
            return (T) new MyFilmViewModel(filmHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static MyFilmViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MyFilmViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyFilmViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}