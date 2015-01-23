package ru.ifmo.md.exam1.provider.playlists;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam1.provider.MyProvider;
import ru.ifmo.md.exam1.provider.playlists.PlaylistsColumns;
import ru.ifmo.md.exam1.provider.song.SongColumns;

/**
 * Columns for the {@code playlists} table.
 */
public class PlaylistsColumns implements BaseColumns {
    public static final String TABLE_NAME = "playlists";
    public static final Uri CONTENT_URI = Uri.parse(MyProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

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
