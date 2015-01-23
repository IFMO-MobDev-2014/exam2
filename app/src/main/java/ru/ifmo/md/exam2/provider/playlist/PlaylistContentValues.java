/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlist;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code playlist} table.
 */
public class PlaylistContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PlaylistColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, PlaylistSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PlaylistContentValues putName(String value) {
        if (value == null) throw new IllegalArgumentException("value for name must not be null");
        mContentValues.put(PlaylistColumns.NAME, value);
        return this;
    }


}
