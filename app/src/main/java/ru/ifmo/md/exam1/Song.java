package ru.ifmo.md.exam1;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Алексей on 23.01.2015.
 */
public class Song {
    int id = -1;
    String artist;
    String name;
    int duration;
    int popularity;
    String[] genres;
    int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Song(String artist, String name, int duration, int popularity, String genres, int year) {
        this.artist = artist;
        this.name = name;
        this.duration = duration;
        this.popularity = popularity;
        this.genres = genres.split("\\|");
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String genres[]) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ContentValues getCV() {
        ContentValues cv = new ContentValues();
        cv.put(SongsDBHelper.SONG_ARTIST, artist);
        cv.put(SongsDBHelper.SONG_DURATION, duration);
        String genre = "";
        for (int i = 0; i < genres.length; i++) {
            if (i != 0) {
                genre += "|";
            }
            genre += genres[i];
        }
        cv.put(SongsDBHelper.SONG_GENRE, genre);
        cv.put(SongsDBHelper.SONG_NAME, name);
        cv.put(SongsDBHelper.SONG_POPULARITY, popularity);
        cv.put(SongsDBHelper.SONG_YEAR, year);
        return cv;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", popularity=" + popularity +
                ", genres=" + Arrays.toString(genres) +
                ", year=" + year +
                '}';
    }
}
