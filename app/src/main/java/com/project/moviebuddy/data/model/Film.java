package com.project.moviebuddy.data.model;

import java.io.Serializable;

public class Film implements Serializable {
    private String imagePath;
    private int userID;
    private int filmID;
    private String filmName;
    private String filmDesc;
    private float filmRating;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmDesc() {
        return filmDesc;
    }

    public void setFilmDesc(String filmDesc) {
        this.filmDesc = filmDesc;
    }

    public float getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(float filmRating) {
        this.filmRating = filmRating;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
}
