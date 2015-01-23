/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlisttotrack;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractSelection;
import ru.ifmo.md.exam2.provider.playlist.*;

/**
 * Selection for the {@code playlist_to_track} table.
 */
public class PlaylistToTrackSelection extends AbstractSelection<PlaylistToTrackSelection> {
    @Override
    public Uri uri() {
        return PlaylistToTrackColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PlaylistToTrackCursor} object, which is positioned before the first entry, or null.
     */
    public PlaylistToTrackCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PlaylistToTrackCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PlaylistToTrackCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PlaylistToTrackCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public PlaylistToTrackSelection id(long... value) {
        addEquals("playlist_to_track." + PlaylistToTrackColumns._ID, toObjectArray(value));
        return this;
    }


    public PlaylistToTrackSelection playlistId(long... value) {
        addEquals(PlaylistToTrackColumns.PLAYLIST_ID, toObjectArray(value));
        return this;
    }

    public PlaylistToTrackSelection playlistIdNot(long... value) {
        addNotEquals(PlaylistToTrackColumns.PLAYLIST_ID, toObjectArray(value));
        return this;
    }

    public PlaylistToTrackSelection playlistIdGt(long value) {
        addGreaterThan(PlaylistToTrackColumns.PLAYLIST_ID, value);
        return this;
    }

    public PlaylistToTrackSelection playlistIdGtEq(long value) {
        addGreaterThanOrEquals(PlaylistToTrackColumns.PLAYLIST_ID, value);
        return this;
    }

    public PlaylistToTrackSelection playlistIdLt(long value) {
        addLessThan(PlaylistToTrackColumns.PLAYLIST_ID, value);
        return this;
    }

    public PlaylistToTrackSelection playlistIdLtEq(long value) {
        addLessThanOrEquals(PlaylistToTrackColumns.PLAYLIST_ID, value);
        return this;
    }

    public PlaylistToTrackSelection playlistName(String... value) {
        addEquals(PlaylistColumns.NAME, value);
        return this;
    }

    public PlaylistToTrackSelection playlistNameNot(String... value) {
        addNotEquals(PlaylistColumns.NAME, value);
        return this;
    }

    public PlaylistToTrackSelection playlistNameLike(String... value) {
        addLike(PlaylistColumns.NAME, value);
        return this;
    }

    public PlaylistToTrackSelection trackId(long... value) {
        addEquals(PlaylistToTrackColumns.TRACK_ID, toObjectArray(value));
        return this;
    }

    public PlaylistToTrackSelection trackIdNot(long... value) {
        addNotEquals(PlaylistToTrackColumns.TRACK_ID, toObjectArray(value));
        return this;
    }

    public PlaylistToTrackSelection trackIdGt(long value) {
        addGreaterThan(PlaylistToTrackColumns.TRACK_ID, value);
        return this;
    }

    public PlaylistToTrackSelection trackIdGtEq(long value) {
        addGreaterThanOrEquals(PlaylistToTrackColumns.TRACK_ID, value);
        return this;
    }

    public PlaylistToTrackSelection trackIdLt(long value) {
        addLessThan(PlaylistToTrackColumns.TRACK_ID, value);
        return this;
    }

    public PlaylistToTrackSelection trackIdLtEq(long value) {
        addLessThanOrEquals(PlaylistToTrackColumns.TRACK_ID, value);
        return this;
    }
}
