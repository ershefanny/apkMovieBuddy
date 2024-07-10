package com.project.moviebuddy.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MovieBuddy.DB";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_USER_NOTE = "CREATE TABLE " + DatabaseContract.UserColumns.TABLE_NAME +
            " (" + DatabaseContract.UserColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + DatabaseContract.UserColumns.USERNAME + " TEXT NOT NULL," +
            " " + DatabaseContract.UserColumns.EMAIL + " TEXT NOT NULL," +
            " " + DatabaseContract.UserColumns.PASSWORD + " TEXT NOT NULL)";

    private static final String SQL_CREATE_FILM_NOTE = "CREATE TABLE " + DatabaseContract.FilmColumns.TABLE_NAME +
            " (" + DatabaseContract.FilmColumns._ID + " INTEGER PRIMARY KEY," +
            " " + DatabaseContract.FilmColumns.USER_ID + " INTEGER NOT NULL," +
            " " + DatabaseContract.FilmColumns.NAME + " TEXT NOT NULL," +
            " " + DatabaseContract.FilmColumns.DESC + " TEXT NOT NULL," +
            " " + DatabaseContract.FilmColumns.IMAGE_PATH + " TEXT NOT NULL," +
            " " + DatabaseContract.FilmColumns.RATING + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_NOTE);
        sqLiteDatabase.execSQL(SQL_CREATE_FILM_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FilmColumns.TABLE_NAME);
        onCreate(db);
    }
}
