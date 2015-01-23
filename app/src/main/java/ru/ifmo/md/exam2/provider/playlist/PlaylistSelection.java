/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlist;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractSelection;

/**
 * Selection for the {@code playlist} table.
 */
public class PlaylistSelection extends AbstractSelection<PlaylistSelection> {
    @Override
    public Uri uri() {
        return PlaylistColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PlaylistCursor} object, which is positioned before the first entry, or null.
     */
    public PlaylistCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PlaylistCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PlaylistCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PlaylistCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PlaylistSelection id(long... value) {
        addEquals("playlist." + PlaylistColumns._ID, toObjectArray(value));
        return this;
    }


    public PlaylistSelection name(String... value) {
        addEquals(PlaylistColumns.NAME, value);
        return this;
    }

    public PlaylistSelection nameNot(String... value) {
        addNotEquals(PlaylistColumns.NAME, value);
        return this;
    }

    public PlaylistSelection nameLike(String... value) {
        addLike(PlaylistColumns.NAME, value);
        return this;
    }
}
