package ru.ifmo.md.exam1.provider.song;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam1.provider.MyProvider;
import ru.ifmo.md.exam1.provider.playlists.PlaylistsColumns;
import ru.ifmo.md.exam1.provider.song.SongColumns;

/**
 * Columns for the {@code song} table.
 */
public class SongColumns implements BaseColumns {
    public static final String TABLE_NAME = "song";
    public static final Uri CONTENT_URI = Uri.parse(MyProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String ARTIST = "artist";

    public static final String SONG = "song";

    public static final String URL = "url";

    public static final String DURATION = "duration";

    public static final String POPULARITY = "popularity";

    public static final String GENRES_MASK = "genres_mask";

    public static final String YEAR = "year";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ARTIST,
            SONG,
            URL,
            DURATION,
            POPULARITY,
            GENRES_MASK,
            YEAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == ARTIST || c.contains("." + ARTIST)) return true;
            if (c == SONG || c.contains("." + SONG)) return true;
            if (c == URL || c.contains("." + URL)) return true;
            if (c == DURATION || c.contains("." + DURATION)) return true;
            if (c == POPULARITY || c.contains("." + POPULARITY)) return true;
            if (c == GENRES_MASK || c.contains("." + GENRES_MASK)) return true;
            if (c == YEAR || c.contains("." + YEAR)) return true;
        }
        return false;
    }

}
