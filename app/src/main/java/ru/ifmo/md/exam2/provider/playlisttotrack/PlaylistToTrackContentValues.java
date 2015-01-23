/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlisttotrack;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam2.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code playlist_to_track} table.
 */
public class PlaylistToTrackContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PlaylistToTrackColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, PlaylistToTrackSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PlaylistToTrackContentValues putPlaylistId(long value) {
        mContentValues.put(PlaylistToTrackColumns.PLAYLIST_ID, value);
        return this;
    }



    public PlaylistToTrackContentValues putTrackId(long value) {
        mContentValues.put(PlaylistToTrackColumns.TRACK_ID, value);
        return this;
    }


}
