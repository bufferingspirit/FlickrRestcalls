package com.example.admin.flickrrestcalls;

import android.graphics.Bitmap;

/**
 * Created by Admin on 8/17/2017.
 */

public class Entry {

    String author;
    String date_taken;
    String pictureURL;

    public Entry(String author, String date_taken, String pictureURL) {
        this.author = author;
        this.date_taken = date_taken;
        this.pictureURL = pictureURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
