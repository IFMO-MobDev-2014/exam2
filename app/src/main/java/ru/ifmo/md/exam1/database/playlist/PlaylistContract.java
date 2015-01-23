package ru.ifmo.md.exam1.database.playlist;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vadim on 23/01/15.
 */
public class PlaylistContract {
    public interface PlaylistColumns {
        public static final String ID = "playlist_id";
        public static final String NAME = "playlist_name";
        public static final String SONGS = "playlist_songs";
        public static final String VALID_STATE = "playlist_valid_state";
    }

    public static final String AUTHORITY = PlaylistContract.class.getCanonicalName();

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH = "playlist";

    public static final class Playlist implements PlaylistColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String[] ALL_COLUMNS = {
                Playlist._ID,
                Playlist.ID,
                Playlist.NAME,
                Playlist.SONGS,
                Playlist.VALID_STATE
        };

        public static Uri buildPhotoUri(String songId) {
            return CONTENT_URI.buildUpon().appendPath(songId).build();
        }
    }
}
