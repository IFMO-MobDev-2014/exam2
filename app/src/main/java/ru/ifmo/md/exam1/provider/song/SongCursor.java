package ru.ifmo.md.exam1.provider.song;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam1.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code song} table.
 */
public class SongCursor extends AbstractCursor {
    public SongCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code artist} value.
     * Can be {@code null}.
     */
    public String getArtist() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.ARTIST);
        return getString(index);
    }

    /**
     * Get the {@code song} value.
     * Can be {@code null}.
     */
    public String getSong() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.SONG);
        return getString(index);
    }

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    public String getUrl() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.URL);
        return getString(index);
    }

    /**
     * Get the {@code duration} value.
     * Can be {@code null}.
     */
    public String getDuration() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.DURATION);
        return getString(index);
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    public String getPopularity() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.POPULARITY);
        return getString(index);
    }

    /**
     * Get the {@code genres_mask} value.
     * Can be {@code null}.
     */
    public String getGenresMask() {
        Integer index = getCachedColumnIndexOrThrow(SongColumns.GENRES_MASK);
        return getString(index);
    }

    /**
     * Get the {@code year} value.
     * Can be {@code null}.
     */
    public Integer getYear() {
        return getIntegerOrNull(SongColumns.YEAR);
    }
}
