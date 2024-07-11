package com.project.moviebuddy.ui.detail;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.utils.Constants;
import com.project.moviebuddy.utils.SharedPreferencesManager;

import java.util.ArrayList;

public class DetailViewModel extends ViewModel {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public DetailViewModel(
            FilmHelper filmHelper,
            SharedPreferencesManager preferencesManager,
            Context context
    ) {
        this.filmHelper = filmHelper;
        this.preferencesManager = preferencesManager;
        this.context = context;

        filmHelper.open();
    }

    public int getUserID() {
        return preferencesManager.getUserID(context);
    }

    public ArrayList<Film> getFilms() {
        Cursor cursor = filmHelper.queryAll();
        return Constants.mapFilmCursorToArrayList(cursor);
    }

    public int deleteFilm(String filmId, String userId) {
        return filmHelper.deleteById(filmId, userId);
    }
}