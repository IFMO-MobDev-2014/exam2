package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.database.Cursor;

public class Playlist {

    public final String name;

    public Playlist(String name) {
        this.name = name;
    }

    public static Playlist fromCursor(Cursor c) {
        return new Playlist(c.getString(c.getColumnIndex(DBAdapter.KEY_PLAYLISTS_NAME)));
    }

    public ContentValues toContentValues() {
        ContentValues result = new ContentValues();
        result.put(DBAdapter.KEY_PLAYLISTS_NAME, name);
        return result;
    }
}
