package ru.ifmo.md.exam1.provider.playlists;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code playlists} table.
 */
public class PlaylistsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PlaylistsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, PlaylistsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PlaylistsContentValues putName(String value) {
        mContentValues.put(PlaylistsColumns.NAME, value);
        return this;
    }

    public PlaylistsContentValues putNameNull() {
        mContentValues.putNull(PlaylistsColumns.NAME);
        return this;
    }

}
