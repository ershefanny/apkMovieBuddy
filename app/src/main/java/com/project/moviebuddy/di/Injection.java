package com.project.moviebuddy.di;

import android.content.Context;

import com.project.moviebuddy.data.local.helpers.FilmHelper;
import com.project.moviebuddy.data.local.helpers.UserHelper;
import com.project.moviebuddy.utils.SharedPreferencesManager;

public class Injection {
    public static UserHelper getUserHelperInstance(Context context) {
        return new UserHelper(context);
    }

    public static FilmHelper getFilmHelperInstance(Context context) {
        return new FilmHelper(context);
    }

    public static SharedPreferencesManager getSharedPreferenceManagerInstance() {
        return new SharedPreferencesManager();
    }
}
