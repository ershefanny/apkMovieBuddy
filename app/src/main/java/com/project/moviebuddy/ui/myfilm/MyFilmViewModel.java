package com.project.moviebuddy.ui.myfilm;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.data.model.Film;
import com.project.moviebuddy.utils.Constants;
import com.project.moviebuddy.utils.SharedPreferencesManager;

import java.util.ArrayList;

public class MyFilmViewModel extends ViewModel {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public MyFilmViewModel(
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


    public ArrayList<Film> getMyFilms(String userId) {
        Cursor cursor = filmHelper.queryByUserId(userId);
        return Constants.mapFilmCursorToArrayList(cursor);
    }
}