/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.track;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam2.provider.MyContentProvider;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.track.TrackColumns;

/**
 * Columns for the {@code track} table.
 */
public class TrackColumns implements BaseColumns {
    public static final String TABLE_NAME = "track";
    public static final Uri CONTENT_URI = Uri.parse(MyContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String TITLE = "title";

    public static final String AUTHOR = "author";

    public static final String YEAR = "year";

    public static final String GENRES_MASK = "genres_mask";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            AUTHOR,
            YEAR,
            GENRES_MASK
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == TITLE || c.contains("." + TITLE)) return true;
            if (c == AUTHOR || c.contains("." + AUTHOR)) return true;
            if (c == YEAR || c.contains("." + YEAR)) return true;
            if (c == GENRES_MASK || c.contains("." + GENRES_MASK)) return true;
        }
        return false;
    }

}
