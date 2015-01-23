package ru.ifmo.md.exam1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ItemsListProvider extends ContentProvider implements BaseColumns {
    public static final String SONGS = "Songs";
    public static final String ARTIST = "Artist";
    public static final String NAME = "Name";
    public static final String URL = "Url";
    public static final String POPULARITY = "Popularity";
    public static final String YEAR = "Year";

    public static final String GENRES = "Genres";
    public static final String GENRE = "GenreName";

    public static final String GENRE_LIST = "GenreList";
    public static final String SONG_REF = "SongRef";
    public static final String GENRE_REF = "GenreRef";

    public static final String PLAYLIST_NAMES = "Playlists";
    public static final String PL_NAME = "PlaylistName";

    public static final String PLAYLISTS = "PlaylistData";
    public static final String PLAYLIST_REF = "PlaylistRef";
    public static final String SONG_INFO = "SongInfo";
    public static final String DELETE_SONG = "content://net.dimatomp.exam0.provider/delete_song";
    public static final String CREATE_PLAYLIST = "content://net.dimatomp.exam0.provider/new_playlist";
    public static final String INSERT_SONG = "content://net.dimatomp.exam0.provider/add_song";
    public static final String FETCH_SONGS = "content://net.dimatomp.exam0.provider/songs";
    public static final String FETCH_PLAYLIST = "content://net.dimatomp.exam0.provider/playlist";
    public static final String FETCH_ALL_PLS = "content://net.dimatomp.exam0.provider/playlistNames";
    private static ObjectMapper mapper = new ObjectMapper();
    DBHelper helper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        if (uri.getPathSegments().size() == 1 && "delete_song".equals(uri.getLastPathSegment())) {
            SQLiteDatabase db = helper.getWritableDatabase();
            db.delete(SONGS, "_ID = ?", new String[]{uri.getQueryParameter("songId")});
            return 1;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uri.getPathSegments().size() == 1) {
            SQLiteDatabase db = helper.getWritableDatabase();
            values = new ContentValues(2);
            switch (uri.getLastPathSegment()) {
                case "new_playlist":
                    values.put(PL_NAME, uri.getQueryParameter("name"));
                    db.insert(PLAYLIST_NAMES, null, values);
                    return null;
                case "add_song":
                    values.put(SONG_REF, Long.parseLong(uri.getQueryParameter("songId")));
                    values.put(PLAYLIST_REF, Long.parseLong(uri.getQueryParameter("playlistId")));
                    db.insert(PLAYLISTS, null, values);
                    return null;
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        if (uri.getPathSegments().size() == 1) {
            SQLiteDatabase db = helper.getReadableDatabase();
            switch (uri.getLastPathSegment()) {
                case "songs":
                    return db.query(SONGS, null, null, null, null, null, null);
                case "playlist":
                    return db.rawQuery("SELECT * FROM " + SONGS + " WHERE " + _ID +
                                    " = (SELECT " + SONG_INFO + " FROM " + PLAYLISTS +
                                    " WHERE " + PLAYLIST_REF + " = ?)",
                            new String[]{uri.getQueryParameter("playlistId")});
                case "playlistNames":
                    return db.query(PLAYLIST_NAMES, null, null, null, null, null, null);
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static class Song {
        public String name;
        public String url;
        public String duration;
        public float popularity;
        public List<String> genres;
        public int year;

        public Song() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public float getPopularity() {
            return popularity;
        }

        public void setPopularity(float popularity) {
            this.popularity = popularity;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    class DBHelper extends SQLiteOpenHelper {
        DBHelper(Context context) {
            super(context, "items.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("ItemsListProvider", "Initial launch");

            db.execSQL("CREATE TABLE " + SONGS + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ARTIST + " TEXT, " +
                    NAME + " TEXT, " +
                    POPULARITY + " FLOAT, " +
                    URL + " TEXT NOT NULL, " +
                    YEAR + " INTEGER);");
            db.execSQL("CREATE TABLE " + GENRES + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GENRE + " TEXT UNIQUE NOT NULL);");
            db.execSQL("CREATE TABLE " + GENRE_LIST + " (" +
                    SONG_REF + " INTEGER NOT NULL, " +
                    GENRE_REF + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + SONG_REF + ") REFERENCES " + SONGS + " (" + _ID + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY(" + GENRE_REF + ") REFERENCES " + GENRES + " (" + _ID + ") ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE " + PLAYLIST_NAMES + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PL_NAME + " TEXT);");
            db.execSQL("CREATE TABLE " + PLAYLISTS + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLAYLIST_REF + " INTEGER NOT NULL, " +
                    SONG_INFO + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + SONG_INFO + ") REFERENCES " + SONGS + "(" + _ID + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY(" + PLAYLIST_REF + ") REFERENCES " + PLAYLIST_NAMES + "(" + _ID + ") ON DELETE CASCADE);");

            ObjectMapper mapper = new ObjectMapper();
            db.beginTransaction();
            try {
                Song[] songs = mapper.readValue(
                        getContext().getResources().openRawResource(R.raw.music), Song[].class);
                HashSet<String> genres = new HashSet<>();
                for (Song song : songs) {
                    genres.addAll(song.genres);
                }

                HashMap<String, Long> genresMap = new HashMap<>();
                ContentValues values = new ContentValues(5);
                for (String genre : genres) {
                    values.put(GENRE, genre);
                    genresMap.put(genre, db.insert(GENRES, null, values));
                }
                values.clear();

                for (Song song : songs) {
                    int ind = song.name.indexOf(" – ");
                    if (ind == -1) {
                        values.put(NAME, song.name);
                        Log.w("ItemsListProvider", "Bad formatted name: " + song.name);
                    } else {
                        values.put(ARTIST, song.name.substring(0, ind));
                        values.put(NAME, song.name.substring(ind + 3, song.name.length()));
                    }
                    values.put(POPULARITY, song.popularity);
                    values.put(YEAR, song.year);
                    values.put(URL, song.url);
                    long rowId = db.insert(SONGS, null, values);
                    if (!song.genres.isEmpty()) {
                        StringBuilder builder = new StringBuilder(
                                "INSERT INTO " + GENRE_LIST + " (" + SONG_REF + ", " + GENRE_REF + ") VALUES ");
                        boolean first = true;
                        for (String genre : song.genres) {
                            if (!first) {
                                builder.append(", ");
                            }
                            first = false;
                            builder.append("(").append(rowId)
                                    .append(", ").append(genresMap.get(genre)).append(")");
                        }
                        String q = builder.append(';').toString();
                        //Log.v("ItemsListProvider", q);
                        db.execSQL(q);
                    }
                }

                db.setTransactionSuccessful();
                Log.d("ItemsListProvider", "Transaction successful");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            Log.d("ItemsListProvider", "Initialization finished");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SONGS + ";");
            db.execSQL("DROP TABLE IF EXISTS " + GENRES + ";");
            db.execSQL("DROP TABLE IF EXISTS " + GENRE_REF + ";");
            db.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_NAMES + ";");
            db.execSQL("DROP TABLE IF EXISTS " + PLAYLISTS + ";");
        }
    }
}
