package ru.eugene.exam2.items;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by eugene on 1/21/15.
 */
public class PlayList implements Serializable {
    private int id;
    private String name;
    private String date;
    private String artist;
    private int year;
    private String genres;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues generateContentValues() {
        ContentValues value = new ContentValues();

        value.put(PlayListsSource.COLUMN_NAME ,name);
        value.put(PlayListsSource.COLUMN_DATE ,date);
        value.put(PlayListsSource.COLUMN_ARTIST ,artist);
        value.put(PlayListsSource.COLUMN_YEAR ,year);
        value.put(PlayListsSource.COLUMN_GENRES, genres);

        return value;
    }
}
