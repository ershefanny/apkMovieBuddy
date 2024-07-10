package com.project.moviebuddy.utils;

import android.database.Cursor;

import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.data.model.Film;

import java.util.ArrayList;

public class Constants {
    public static ArrayList<Film> mapFilmCursorToArrayList(Cursor filmCursor) {
        ArrayList<Film> filmArrayList = new ArrayList<>();

        if (filmCursor != null) {
            while (filmCursor.moveToNext()) {
                int filmId = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns._ID));
                int userId = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.USER_ID));
                String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.NAME));
                String desc = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.DESC));
                String imagePath = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.IMAGE_PATH));
                float rating = filmCursor.getFloat(filmCursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns.RATING));

                Film film = new Film();
                film.setFilmID(filmId);
                film.setUserID(userId);
                film.setFilmName(title);
                film.setFilmDesc(desc);
                film.setImagePath(imagePath);
                film.setFilmRating(rating);

                filmArrayList.add(film);
            }
        }
        return filmArrayList;
    }
}
