package com.project.moviebuddy.ui.main.ui.addfilm;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.login.LoginViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class AddFilmViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    private static volatile AddFilmViewModelFactory INSTANCE;

    private AddFilmViewModelFactory(Context context) {
        this.filmHelper = Injection.getFilmHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddFilmViewModel.class)) {
            return (T) new AddFilmViewModel(filmHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static AddFilmViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AddFilmViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AddFilmViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}