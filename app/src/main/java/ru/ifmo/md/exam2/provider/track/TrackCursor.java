/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.track;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam2.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code track} table.
 */
public class TrackCursor extends AbstractCursor {
    public TrackCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    public String getTitle() {
        Integer index = getCachedColumnIndexOrThrow(TrackColumns.TITLE);
        return getString(index);
    }

    /**
     * Get the {@code author} value.
     * Cannot be {@code null}.
     */
    public String getAuthor() {
        Integer index = getCachedColumnIndexOrThrow(TrackColumns.AUTHOR);
        return getString(index);
    }

    /**
     * Get the {@code year} value.
     */
    public int getYear() {
        return getIntegerOrNull(TrackColumns.YEAR);
    }

    /**
     * Get the {@code genres_mask} value.
     */
    public long getGenresMask() {
        return getLongOrNull(TrackColumns.GENRES_MASK);
    }
}
