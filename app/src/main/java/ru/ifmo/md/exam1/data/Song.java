package ru.ifmo.md.exam1.data;

import android.content.ContentValues;
import android.util.Log;

import java.util.List;

/**
 * Created by german on 22.01.15.
 */
public class Song {
    private String name;
    private String url;
    private String duration;
    private int popularity;
    List<String> genres;
    private int year;

    public ContentValues getCV() {
        ContentValues cv = new ContentValues();
        cv.put(MyProvider.SONG_URL, url);
        cv.put(MyProvider.SONG_DUR, duration);
        cv.put(MyProvider.SONG_POP, popularity);
        cv.put(MyProvider.SONG_YEAR, year);
        String[] names = name.split(" – ");
        cv.put(MyProvider.SONG_ARTIST, names[0]);
        cv.put(MyProvider.SONG_NAME, names[1]);
        return cv;
    }

}
