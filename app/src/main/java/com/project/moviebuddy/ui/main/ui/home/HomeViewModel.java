package com.project.moviebuddy.ui.main.ui.home;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.data.model.User;
import com.project.moviebuddy.utils.Constants;
import com.project.moviebuddy.utils.SharedPreferencesManager;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public HomeViewModel(
            FilmHelper filmHelper,
            SharedPreferencesManager preferencesManager,
            Context context
    ) {
        this.filmHelper = filmHelper;
        this.preferencesManager = preferencesManager;
        this.context = context;

        filmHelper.open();
    }

    public User getSavedUser() {
        return preferencesManager.getSavedUser(context);
    }

    public ArrayList<Film> getFilms() {
        Cursor cursor = filmHelper.queryAll();
        return Constants.mapFilmCursorToArrayList(cursor);
    }
}