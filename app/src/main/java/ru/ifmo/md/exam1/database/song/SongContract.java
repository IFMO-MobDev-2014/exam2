package ru.ifmo.md.exam1.database.song;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vadim on 23/01/15.
 */
public class SongContract {
    public interface SongColumns {
        public static final String ID = "song_id";
        public static final String NAME = "song_name";
        public static final String URL = "song_url";
        public static final String DURATION = "song_duration";
        public static final String POPULARITY = "song_url_thumbnail";
        public static final String YEAR = "song_year";
        public static final String GENRES = "song_genres";
        public static final String VALID_STATE = "song_valid_state";
    }

    public static final String AUTHORITY = SongContract.class.getCanonicalName();

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH = "songs";

    public static final class Song implements SongColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String[] ALL_COLUMNS = {
                Song._ID,
                Song.ID,
                Song.NAME,
                Song.URL,
                Song.DURATION,
                Song.POPULARITY,
                Song.YEAR,
                Song.GENRES,
                Song.VALID_STATE
        };

        public static Uri buildPhotoUri(String songId) {
            return CONTENT_URI.buildUpon().appendPath(songId).build();
        }
    }
}
