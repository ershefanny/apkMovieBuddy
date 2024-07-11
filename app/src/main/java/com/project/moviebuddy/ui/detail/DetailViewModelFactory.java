package com.project.moviebuddy.ui.detail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.di.Injection;
import com.project.moviebuddy.ui.myfilm.MyFilmViewModel;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;
 
    private static volatile DetailViewModelFactory INSTANCE;

    private DetailViewModelFactory(Context context) {
        this.filmHelper = Injection.getFilmHelperInstance(context);
        this.preferencesManager = Injection.getSharedPreferenceManagerInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(filmHelper, preferencesManager, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static DetailViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DetailViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DetailViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
}