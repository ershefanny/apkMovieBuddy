package com.project.moviebuddy.data.local;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static class UserColumns implements BaseColumns {
        public static String TABLE_NAME = "Users";
        public static String _ID = "UserID";
        public static String USERNAME = "Username";
        public static String EMAIL = "Email";
        public static String PASSWORD = "Password";
    }

    public static class FilmColumns implements BaseColumns {
        public static String TABLE_NAME = "Films";
        public static String _ID = "FilmID";
        public static String USER_ID = "UserID";
        public static String NAME = "FilmName";
        public static String DESC = "FilmDesc";
        public static String IMAGE_PATH = "FilmImagePath";
        public static String RATING = "FilmRating";
    }
}
