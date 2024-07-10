package com.project.moviebuddy.data.local.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.data.local.DatabaseContract.UserColumns;
import com.project.moviebuddy.data.local.DatabaseHelper;

public class FilmHelper {

    private static final String DATABASE_TABLE = DatabaseContract.FilmColumns.TABLE_NAME;
    private static FilmHelper INSTANCE;

    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FilmHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static synchronized FilmHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FilmHelper(context);
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null, null,
                null,
                null,
                DatabaseContract.FilmColumns._ID + " ASC" // Replace YourTableNameColumns
        );
    }

    public Cursor queryByUserId(String userID) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.FilmColumns.USER_ID + " = ?",
                new String[]{userID},
                null,
                null,
                null,
                null
        );
    }

    public int deleteById(String filmId, String userId) {
        return database.delete(
                DATABASE_TABLE,
                DatabaseContract.FilmColumns.USER_ID + " = ? AND " + DatabaseContract.FilmColumns._ID + " = ?",
                new String[]{userId, filmId}
        );
    }

    public int updateByFilmId(String filmId, String userId, ContentValues values) {
        return database.update(
                DATABASE_TABLE,
                values,
                DatabaseContract.FilmColumns._ID + " = ? AND " + DatabaseContract.FilmColumns.USER_ID + " = ?",
                new String[]{filmId, userId}
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
}