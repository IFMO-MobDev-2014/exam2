package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.database.Cursor;

public class Genre {

    public final String name;

    public Genre(String name) {
        this.name = name;
    }

    public static Genre fromCursor(Cursor c) {
        return new Genre(c.getString(c.getColumnIndex(DBAdapter.KEY_GENRES_NAME)));
    }

    public ContentValues toContentValues() {
        ContentValues result = new ContentValues();
        result.put(DBAdapter.KEY_GENRES_NAME, name);
        return result;
    }
}
