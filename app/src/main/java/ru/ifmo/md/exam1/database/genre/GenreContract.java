package ru.ifmo.md.exam1.database.genre;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam1.database.song.SongContract;

/**
 * Created by vadim on 23/01/15.
 */
public class GenreContract {
    public interface SongColumns {
        public static final String ID = "genre_id";
        public static final String NAME = "genre_name";
        public static final String VALID_STATE = "genre_valid_state";
    }

    public static final String AUTHORITY = SongContract.class.getCanonicalName();

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH = "genres";

    public static final class Genre implements SongColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String[] ALL_COLUMNS = {
                Genre._ID,
                Genre.ID,
                Genre.NAME,
                Genre.VALID_STATE
        };

        public static Uri buildPhotoUri(String songId) {
            return CONTENT_URI.buildUpon().appendPath(songId).build();
        }
    }
}
