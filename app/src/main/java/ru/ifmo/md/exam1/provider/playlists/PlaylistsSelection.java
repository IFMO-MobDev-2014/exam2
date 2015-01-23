package ru.ifmo.md.exam1.provider.playlists;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractSelection;

/**
 * Selection for the {@code playlists} table.
 */
public class PlaylistsSelection extends AbstractSelection<PlaylistsSelection> {
    @Override
    public Uri uri() {
        return PlaylistsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PlaylistsCursor} object, which is positioned before the first entry, or null.
     */
    public PlaylistsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PlaylistsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PlaylistsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PlaylistsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PlaylistsSelection id(long... value) {
        addEquals("playlists." + PlaylistsColumns._ID, toObjectArray(value));
        return this;
    }


    public PlaylistsSelection name(String... value) {
        addEquals(PlaylistsColumns.NAME, value);
        return this;
    }

    public PlaylistsSelection nameNot(String... value) {
        addNotEquals(PlaylistsColumns.NAME, value);
        return this;
    }

    public PlaylistsSelection nameLike(String... value) {
        addLike(PlaylistsColumns.NAME, value);
        return this;
    }
}
