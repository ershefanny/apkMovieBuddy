package com.project.moviebuddy.ui.main.ui.addfilm;

import android.content.ContentValues;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.data.local.helpers.UserHelper;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class AddFilmViewModel extends ViewModel {
    private final FilmHelper filmHelper;
    private final SharedPreferencesManager preferencesManager;
    private final Context context;

    public AddFilmViewModel(
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

    public long addFilm(ContentValues values) {
        return filmHelper.insert(values);
    }
}