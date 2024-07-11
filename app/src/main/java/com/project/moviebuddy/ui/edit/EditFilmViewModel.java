package com.project.moviebuddy.ui.edit;

import android.content.ContentValues;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class EditFilmViewModel extends ViewModel {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public EditFilmViewModel(
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

    public int updateFilm(String filmId, String userId, ContentValues values) {
        return filmHelper.updateByFilmId(filmId, userId, values);
    }
}