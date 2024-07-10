package com.project.moviebuddy.ui.edit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.main.ui.addfilm.AddFilmViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class EditFilmViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    private static volatile EditFilmViewModelFactory INSTANCE;

    private EditFilmViewModelFactory(Context context) {
        this.filmHelper = Injection.getFilmHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditFilmViewModel.class)) {
            return (T) new EditFilmViewModel(filmHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static EditFilmViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EditFilmViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EditFilmViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}