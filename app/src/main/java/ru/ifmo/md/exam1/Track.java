package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public class Track {

    public final String name;
    public final String artist;
    public final int year;
    public final int popularity;
    public final List<Genre> genres;

    public Track(String name, String artist, int year, int popularity, List<Genre> genres) {
        this.name = name;
        this.artist = artist;
        this.year = year;
        this.popularity = popularity;
        this.genres = genres;
    }

    public static Track fromCursor(Cursor c) {
        String name = c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_NAME));
        String artist = c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_ARTIST));
        int year = c.getInt(c.getColumnIndex(DBAdapter.KEY_TRACKS_YEAR));
        int popularity = c.getInt(c.getColumnIndex(DBAdapter.KEY_TRACKS_POPULARITY));
        return new Track(name, artist, year, popularity, null);
    }

    public ContentValues toContentValues() {
        ContentValues result = new ContentValues();
        result.put(DBAdapter.KEY_TRACKS_NAME, name);
        result.put(DBAdapter.KEY_TRACKS_ARTIST, artist);
        result.put(DBAdapter.KEY_TRACKS_YEAR, year);
        result.put(DBAdapter.KEY_TRACKS_POPULARITY, popularity);
        StringBuilder genreNames = new StringBuilder();
        for (Genre g: genres) {
            genreNames.append(g.name).append(", ");
        }
        result.put(DBAdapter.KEY_TRACKS_GENRES, genreNames.toString());
        return result;
    }
}
