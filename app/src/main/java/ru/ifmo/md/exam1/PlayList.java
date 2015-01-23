package ru.ifmo.md.exam1;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by Алексей on 23.01.2015.
 */
public class PlayList {
    int id;
    String name;
    List<Integer> songsId;
    List<Song> songs;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public PlayList(String name, List<Integer> songs) {
        this.name = name;
        this.songsId = songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSongsId() {
        return songsId;
    }

    public void setSongsId(List<Integer> songs) {
        this.songsId = songs;
    }

    public ContentValues getCV() {
        ContentValues cv = new ContentValues();
        cv.put(SongsDBHelper.PLAYLIST_NAME, name);
        StringBuilder sb = new StringBuilder();
        for (int i : songsId) {
            if (sb.length() != 0) {
                sb.append("|");
            }
            sb.append(id);
        }
        cv.put(SongsDBHelper.PLAYLIST_SONGS, sb.toString());
        return cv;
    }
}
