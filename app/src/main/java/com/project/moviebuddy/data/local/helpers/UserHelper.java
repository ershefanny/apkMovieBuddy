package com.project.moviebuddy.data.local.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.project.moviebuddy.data.local.DatabaseContract;
import com.project.moviebuddy.data.local.DatabaseContract.*;
import com.project.moviebuddy.data.local.DatabaseHelper;

public class UserHelper {

    private static final String DATABASE_TABLE = UserColumns.TABLE_NAME;
    private static UserHelper INSTANCE;

    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public UserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static synchronized UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserHelper(context);
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

    public Cursor loginUser(String username, String password) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.UserColumns.USERNAME + " = ? AND " + DatabaseContract.UserColumns.PASSWORD + " = ?",
                new String[]{username, password},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
}