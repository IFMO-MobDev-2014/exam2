package ru.eugene.exam2.items;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by eugene on 1/21/15.
 */
public class Song implements Serializable {
    private int id;
    private String name;
    private String artist;
    private int year;
    private String url;
    private String duration;
    private int popularity;
    private String genres;
    private int id2;

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

        value.put(SongsSource.COLUMN_NAME, name);
        value.put(SongsSource.COLUMN_YEAR, year);
        value.put(SongsSource.COLUMN_ARTIST, artist);
        value.put(SongsSource.COLUMN_YEAR, year);
        value.put(SongsSource.COLUMN_URL, url);
        value.put(SongsSource.COLUMN_DURATION, duration);
        value.put(SongsSource.COLUMN_POPULARITY, popularity);
        value.put(SongsSource.COLUMN_GENRES, genres);
        value.put(SongsSource.COLUMN_ID2, id2);

        return value;
    }
}
