package ru.ifmo.md.exam1.provider.song;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractSelection;

/**
 * Selection for the {@code song} table.
 */
public class SongSelection extends AbstractSelection<SongSelection> {
    @Override
    public Uri uri() {
        return SongColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code SongCursor} object, which is positioned before the first entry, or null.
     */
    public SongCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new SongCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public SongCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public SongCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public SongSelection id(long... value) {
        addEquals("song." + SongColumns._ID, toObjectArray(value));
        return this;
    }


    public SongSelection artist(String... value) {
        addEquals(SongColumns.ARTIST, value);
        return this;
    }

    public SongSelection artistNot(String... value) {
        addNotEquals(SongColumns.ARTIST, value);
        return this;
    }

    public SongSelection artistLike(String... value) {
        addLike(SongColumns.ARTIST, value);
        return this;
    }

    public SongSelection song(String... value) {
        addEquals(SongColumns.SONG, value);
        return this;
    }

    public SongSelection songNot(String... value) {
        addNotEquals(SongColumns.SONG, value);
        return this;
    }

    public SongSelection songLike(String... value) {
        addLike(SongColumns.SONG, value);
        return this;
    }

    public SongSelection url(String... value) {
        addEquals(SongColumns.URL, value);
        return this;
    }

    public SongSelection urlNot(String... value) {
        addNotEquals(SongColumns.URL, value);
        return this;
    }

    public SongSelection urlLike(String... value) {
        addLike(SongColumns.URL, value);
        return this;
    }

    public SongSelection duration(String... value) {
        addEquals(SongColumns.DURATION, value);
        return this;
    }

    public SongSelection durationNot(String... value) {
        addNotEquals(SongColumns.DURATION, value);
        return this;
    }

    public SongSelection durationLike(String... value) {
        addLike(SongColumns.DURATION, value);
        return this;
    }

    public SongSelection popularity(String... value) {
        addEquals(SongColumns.POPULARITY, value);
        return this;
    }

    public SongSelection popularityNot(String... value) {
        addNotEquals(SongColumns.POPULARITY, value);
        return this;
    }

    public SongSelection popularityLike(String... value) {
        addLike(SongColumns.POPULARITY, value);
        return this;
    }

    public SongSelection genresMask(String... value) {
        addEquals(SongColumns.GENRES_MASK, value);
        return this;
    }

    public SongSelection genresMaskNot(String... value) {
        addNotEquals(SongColumns.GENRES_MASK, value);
        return this;
    }

    public SongSelection genresMaskLike(String... value) {
        addLike(SongColumns.GENRES_MASK, value);
        return this;
    }

    public SongSelection year(Integer... value) {
        addEquals(SongColumns.YEAR, value);
        return this;
    }

    public SongSelection yearNot(Integer... value) {
        addNotEquals(SongColumns.YEAR, value);
        return this;
    }

    public SongSelection yearGt(int value) {
        addGreaterThan(SongColumns.YEAR, value);
        return this;
    }

    public SongSelection yearGtEq(int value) {
        addGreaterThanOrEquals(SongColumns.YEAR, value);
        return this;
    }

    public SongSelection yearLt(int value) {
        addLessThan(SongColumns.YEAR, value);
        return this;
    }

    public SongSelection yearLtEq(int value) {
        addLessThanOrEquals(SongColumns.YEAR, value);
        return this;
    }
}
