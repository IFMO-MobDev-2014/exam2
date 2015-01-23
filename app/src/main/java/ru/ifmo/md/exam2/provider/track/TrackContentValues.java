/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.track;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code track} table.
 */
public class TrackContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TrackColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, TrackSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TrackContentValues putTitle(String value) {
        if (value == null) throw new IllegalArgumentException("value for title must not be null");
        mContentValues.put(TrackColumns.TITLE, value);
        return this;
    }



    public TrackContentValues putAuthor(String value) {
        if (value == null) throw new IllegalArgumentException("value for author must not be null");
        mContentValues.put(TrackColumns.AUTHOR, value);
        return this;
    }



    public TrackContentValues putYear(int value) {
        mContentValues.put(TrackColumns.YEAR, value);
        return this;
    }



    public TrackContentValues putGenresMask(long value) {
        mContentValues.put(TrackColumns.GENRES_MASK, value);
        return this;
    }


}
