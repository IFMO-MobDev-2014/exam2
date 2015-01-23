/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlisttotrack;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam2.provider.MyContentProvider;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.track.TrackColumns;

/**
 * Columns for the {@code playlist_to_track} table.
 */
public class PlaylistToTrackColumns implements BaseColumns {
    public static final String TABLE_NAME = "playlist_to_track";
    public static final Uri CONTENT_URI = Uri.parse(MyContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String PLAYLIST_ID = "playlist_id";

    public static final String TRACK_ID = "track_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PLAYLIST_ID,
            TRACK_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == PLAYLIST_ID || c.contains("." + PLAYLIST_ID)) return true;
            if (c == TRACK_ID || c.contains("." + TRACK_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_PLAYLIST = TABLE_NAME + "__" + PlaylistColumns.TABLE_NAME;
    public static final String PREFIX_TRACK = TABLE_NAME + "__" + TrackColumns.TABLE_NAME;
}
