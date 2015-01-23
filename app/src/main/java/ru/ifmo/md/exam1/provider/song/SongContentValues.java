package ru.ifmo.md.exam1.provider.song;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code song} table.
 */
public class SongContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return SongColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, SongSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public SongContentValues putArtist(String value) {
        mContentValues.put(SongColumns.ARTIST, value);
        return this;
    }

    public SongContentValues putArtistNull() {
        mContentValues.putNull(SongColumns.ARTIST);
        return this;
    }


    public SongContentValues putSong(String value) {
        mContentValues.put(SongColumns.SONG, value);
        return this;
    }

    public SongContentValues putSongNull() {
        mContentValues.putNull(SongColumns.SONG);
        return this;
    }


    public SongContentValues putUrl(String value) {
        mContentValues.put(SongColumns.URL, value);
        return this;
    }

    public SongContentValues putUrlNull() {
        mContentValues.putNull(SongColumns.URL);
        return this;
    }


    public SongContentValues putDuration(String value) {
        mContentValues.put(SongColumns.DURATION, value);
        return this;
    }

    public SongContentValues putDurationNull() {
        mContentValues.putNull(SongColumns.DURATION);
        return this;
    }


    public SongContentValues putPopularity(String value) {
        mContentValues.put(SongColumns.POPULARITY, value);
        return this;
    }

    public SongContentValues putPopularityNull() {
        mContentValues.putNull(SongColumns.POPULARITY);
        return this;
    }


    public SongContentValues putGenresMask(String value) {
        mContentValues.put(SongColumns.GENRES_MASK, value);
        return this;
    }

    public SongContentValues putGenresMaskNull() {
        mContentValues.putNull(SongColumns.GENRES_MASK);
        return this;
    }


    public SongContentValues putYear(Integer value) {
        mContentValues.put(SongColumns.YEAR, value);
        return this;
    }

    public SongContentValues putYearNull() {
        mContentValues.putNull(SongColumns.YEAR);
        return this;
    }

}
