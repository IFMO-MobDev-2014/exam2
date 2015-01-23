/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlist;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam2.provider.MyContentProvider;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.track.TrackColumns;

/**
 * Columns for the {@code playlist} table.
 */
public class PlaylistColumns implements BaseColumns {
    public static final String TABLE_NAME = "playlist";
    public static final Uri CONTENT_URI = Uri.parse(MyContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String NAME = "name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == NAME || c.contains("." + NAME)) return true;
        }
        return false;
    }

}
