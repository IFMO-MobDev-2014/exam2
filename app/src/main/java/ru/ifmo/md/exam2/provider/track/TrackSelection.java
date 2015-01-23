/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.track;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractSelection;

/**
 * Selection for the {@code track} table.
 */
public class TrackSelection extends AbstractSelection<TrackSelection> {
    @Override
    public Uri uri() {
        return TrackColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TrackCursor} object, which is positioned before the first entry, or null.
     */
    public TrackCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TrackCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public TrackCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public TrackCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public TrackSelection id(long... value) {
        addEquals("track." + TrackColumns._ID, toObjectArray(value));
        return this;
    }


    public TrackSelection title(String... value) {
        addEquals(TrackColumns.TITLE, value);
        return this;
    }

    public TrackSelection titleNot(String... value) {
        addNotEquals(TrackColumns.TITLE, value);
        return this;
    }

    public TrackSelection titleLike(String... value) {
        addLike(TrackColumns.TITLE, value);
        return this;
    }

    public TrackSelection author(String... value) {
        addEquals(TrackColumns.AUTHOR, value);
        return this;
    }

    public TrackSelection authorNot(String... value) {
        addNotEquals(TrackColumns.AUTHOR, value);
        return this;
    }

    public TrackSelection authorLike(String... value) {
        addLike(TrackColumns.AUTHOR, value);
        return this;
    }

    public TrackSelection year(int... value) {
        addEquals(TrackColumns.YEAR, toObjectArray(value));
        return this;
    }

    public TrackSelection yearNot(int... value) {
        addNotEquals(TrackColumns.YEAR, toObjectArray(value));
        return this;
    }

    public TrackSelection yearGt(int value) {
        addGreaterThan(TrackColumns.YEAR, value);
        return this;
    }

    public TrackSelection yearGtEq(int value) {
        addGreaterThanOrEquals(TrackColumns.YEAR, value);
        return this;
    }

    public TrackSelection yearLt(int value) {
        addLessThan(TrackColumns.YEAR, value);
        return this;
    }

    public TrackSelection yearLtEq(int value) {
        addLessThanOrEquals(TrackColumns.YEAR, value);
        return this;
    }

    public TrackSelection genresMask(long... value) {
        addEquals(TrackColumns.GENRES_MASK, toObjectArray(value));
        return this;
    }

    public TrackSelection genresMaskNot(long... value) {
        addNotEquals(TrackColumns.GENRES_MASK, toObjectArray(value));
        return this;
    }

    public TrackSelection genresMaskGt(long value) {
        addGreaterThan(TrackColumns.GENRES_MASK, value);
        return this;
    }

    public TrackSelection genresMaskGtEq(long value) {
        addGreaterThanOrEquals(TrackColumns.GENRES_MASK, value);
        return this;
    }

    public TrackSelection genresMaskLt(long value) {
        addLessThan(TrackColumns.GENRES_MASK, value);
        return this;
    }

    public TrackSelection genresMaskLtEq(long value) {
        addLessThanOrEquals(TrackColumns.GENRES_MASK, value);
        return this;
    }
}
