package ru.ifmo.md.exam2;

import android.net.Uri;
import android.provider.BaseColumns;

public class Tables {
    public static final String AUTHORITY =
            "ru.ifmo.md.exam2.provider";

    public static final class Tracks implements BaseColumns {
        private Tracks() {}

        public static final String PATH = "tracks";

        public static final Uri CONTENT_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Tracks.PATH);

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracks.data";

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracks.data";

        public static final String AUTHOR_NAME = "author";

        public static final String TITLE_NAME = "title";

        public static final String URL_NAME = "url";

        public static final String DURATION_NAME = "duration";

        public static final String POPULARITY_NAME = "popularity";

        public static final String GENRES_NAME = "genres";

        public static final String YEAR_NAME = "year";
    }

    public static final class Playlists implements BaseColumns {
        private Playlists() {}

        public static final String PATH = "playlists";

        public static final Uri CONTENT_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Playlists.PATH);

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.playlists.data";

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.playlists.data";

        public static final String TITLE_NAME = "title";

        public static final String AUTHOR_NAME = "author";

        public static final String YEAR_NAME = "year";

        public static final String GENRES_NAME = "genres";
    }
}
