package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.renderscript.Script;

import java.util.ArrayList;
import java.util.HashSet;

public class DBAdapter {
    public static final String KEY_ID = "_id";

    //Tracks
    public static final String TABLE_NAME_TRACKS = "tracks";
    public static final String KEY_TRACKS_NAME = "name";
    public static final String KEY_TRACKS_ARTIST = "artist";
    public static final String KEY_TRACKS_YEAR = "year";
    public static final String KEY_TRACKS_POPULARITY = "popularity";
    public static final String KEY_TRACKS_GENRES = "genres";

    public static final String CREATE_TABLE_TRACKS = "CREATE TABLE " + TABLE_NAME_TRACKS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TRACKS_NAME + " STRING NOT NULL, "
            + KEY_TRACKS_ARTIST + " STRING NOT NULL, "
            + KEY_TRACKS_YEAR + " INTEGER, "
            + KEY_TRACKS_POPULARITY + " INTEGER, "
            + KEY_TRACKS_GENRES + " STRING"
            + ")";

    //Genres
    public static final String TABLE_NAME_GENRES = "genres";
    public static final String KEY_GENRES_NAME = "name";

    public static final String CREATE_TABLE_GENRES = "CREATE TABLE " + TABLE_NAME_GENRES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_GENRES_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + KEY_GENRES_NAME + ") ON CONFLICT IGNORE"
            + ")";

    //Playlists
    public static final String TABLE_NAME_PLAYLISTS = "playlists";
    public static final String KEY_PLAYLISTS_NAME = "name";

    public static final String CREATE_TABLE_PLAYLISTS = "CREATE TABLE " + TABLE_NAME_PLAYLISTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PLAYLISTS_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + KEY_PLAYLISTS_NAME + ") ON CONFLICT IGNORE"
            + ")";

    //Genres for tracks
    public static final String TABLE_NAME_TRACKS_GENRES = "tracks_genres";
    public static final String KEY_TRACKS_GENRES_TRACK_ID = "track_id";
    public static final String KEY_TRACKS_GENRES_GENRE_ID = "genre_id";

    public static final String CREATE_TABLE_TRACKS_GENRES = "CREATE TABLE " + TABLE_NAME_TRACKS_GENRES + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TRACKS_GENRES_TRACK_ID + " INTEGER NOT NULL, "
            + KEY_TRACKS_GENRES_GENRE_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_TRACKS_GENRES_TRACK_ID + ") REFERENCES " + TABLE_NAME_TRACKS + " ("
            + KEY_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_TRACKS_GENRES_GENRE_ID + ") REFERENCES " + TABLE_NAME_GENRES + " ("
            + KEY_ID + ") ON DELETE CASCADE)";

    //Genres for tracks
    public static final String TABLE_NAME_TRACKS_PLAYLISTS = "tracks_playlists";
    public static final String KEY_TRACKS_PLAYLISTS_TRACK_ID = "track_id";
    public static final String KEY_TRACKS_PLAYLISTS_PLAYLIST_ID = "playlist_id";

    public static final String CREATE_TABLE_TRACKS_PLAYLISTS = "CREATE TABLE " + TABLE_NAME_TRACKS_PLAYLISTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TRACKS_PLAYLISTS_TRACK_ID + " INTEGER NOT NULL, "
            + KEY_TRACKS_PLAYLISTS_PLAYLIST_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_TRACKS_PLAYLISTS_TRACK_ID + ") REFERENCES " + TABLE_NAME_TRACKS + " ("
            + KEY_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_TRACKS_PLAYLISTS_PLAYLIST_ID + ") REFERENCES " + TABLE_NAME_PLAYLISTS + " ("
            + KEY_ID + ") ON DELETE CASCADE)";


    private static DBAdapter mInstance = null;
    private Context context;
    private SQLiteDatabase db;

    private DBAdapter(Context context) {
        this.context = context;
    }

    public static DBAdapter getOpenedInstance(Context context) {
        if (mInstance == null)
            mInstance = new DBAdapter(context.getApplicationContext()).open();
        return mInstance;
    }

    private DBAdapter open() {
        DBHelper mDbHelper = new DBHelper(context);
        db = mDbHelper.getWritableDatabase();
        return this;
    }

    public static final String DB_NAME = "database";
    public static final Integer VERSION = 1;

    private static class DBHelper extends SQLiteOpenHelper {
        Context context;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
            this.context = context;
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_TRACKS);
            sqLiteDatabase.execSQL(CREATE_TABLE_GENRES);
            sqLiteDatabase.execSQL(CREATE_TABLE_PLAYLISTS);
            sqLiteDatabase.execSQL(CREATE_TABLE_TRACKS_GENRES);
            sqLiteDatabase.execSQL(CREATE_TABLE_TRACKS_PLAYLISTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        }
    }

    public Cursor getAllTracks() {
        return getTracks(null);
    }

    public Cursor getTracksByArtist(String artist) {
        return getTracks(KEY_TRACKS_ARTIST + "='" + artist+"'");
    }

    public Cursor getTracksByGenre(long genreId) {
        return db.query(TABLE_NAME_TRACKS_GENRES + " AS g JOIN " + TABLE_NAME_TRACKS + " AS t ON " +
                        "g." + KEY_TRACKS_PLAYLISTS_TRACK_ID + "= t." + KEY_ID,
                new String[]{"t." + KEY_ID + " AS " + KEY_ID, KEY_TRACKS_ARTIST, KEY_TRACKS_NAME},
                "g." + KEY_ID + "=" + genreId, null, null, null, null);
    }

    public Cursor getTracks(String where) {
        String sort = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TracksFragment.PREF_SORT_MODE, TracksFragment.PREF_SORT_MODE_DEFAULT)
                .equals(TracksFragment.PREF_SORT_MODE_DEFAULT) ? null : KEY_TRACKS_POPULARITY+ " DESC";
        return db.query(TABLE_NAME_TRACKS,
                new String[]{KEY_ID, KEY_TRACKS_ARTIST, KEY_TRACKS_NAME, KEY_TRACKS_YEAR, KEY_TRACKS_POPULARITY},
                where, null, null, null, sort);
    }

    public Cursor getGenres(String where) {
        return db.query(TABLE_NAME_GENRES,
                new String[]{KEY_ID, KEY_GENRES_NAME},
                where, null, null, null, null);
    }

    public Cursor getPlaylists(String where) {
        return db.query(TABLE_NAME_PLAYLISTS,
                new String[]{KEY_ID, KEY_PLAYLISTS_NAME},
                where, null, null, null, null);
    }

    public ArrayList<String> getArtists() {
        Cursor c = getAllTracks();
        HashSet<String> result = new HashSet<>();
        if (c.moveToFirst()) {
            int column = c.getColumnIndex(KEY_TRACKS_ARTIST);
            do {
                String artist = c.getString(column);
                if (!result.contains(artist))
                    result.add(artist);
            } while (c.moveToNext());
        }
        ArrayList<String> artists = new ArrayList<>();
        artists.addAll(result);
        c.close();
        return artists;
    }

    public ArrayList<String> getYears() {
        Cursor c = getAllTracks();
        HashSet<Integer> result = new HashSet<>();
        if (c.moveToFirst()) {
            int column = c.getColumnIndex(KEY_TRACKS_YEAR);
            do {
                int year = c.getInt(column);
                if (!result.contains(year))
                    result.add(year);
            } while (c.moveToNext());
        }
        ArrayList<String> years = new ArrayList<>();
        for (int y : result)
            years.add(Integer.toString(y));
        c.close();
        return years;
    }

    public long findGenreId(String s) {
        Cursor c = db.query(TABLE_NAME_GENRES, new String[] {KEY_ID}, KEY_GENRES_NAME+"=?",
                new String[] {s}, null, null, null, "1");
        long result = -1;
        if (c.moveToFirst())
            result = c.getLong(0);
        c.close();
        return result;
    }

    public ArrayList<String> getGenres() {
        Cursor c = getGenres(null);
        ArrayList<String> result = new ArrayList<>();
        if (c.moveToFirst()) {
            int column = c.getColumnIndex(KEY_GENRES_NAME);
            do {
                String name = c.getString(column);
                result.add(name);
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    public Cursor getTracksByPlaylistId(long playlistId) {
        String sort = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TracksFragment.PREF_SORT_MODE, TracksFragment.PREF_SORT_MODE_DEFAULT)
                .equals(TracksFragment.PREF_SORT_MODE_DEFAULT) ? null : KEY_TRACKS_POPULARITY+ " DESC";
        return db.query(TABLE_NAME_TRACKS_PLAYLISTS + " AS p JOIN " + TABLE_NAME_TRACKS + " AS t ON " +
                        "p." + KEY_TRACKS_PLAYLISTS_TRACK_ID + "= t." + KEY_ID,
                new String[]{"t." + KEY_ID + " AS " + KEY_ID, KEY_TRACKS_ARTIST, KEY_TRACKS_NAME, KEY_TRACKS_YEAR, KEY_TRACKS_POPULARITY},
                "p." + KEY_ID + "=" + playlistId, null, null, null, sort);

    }

    public long addGenre(ContentValues cv) {
        Cursor c = db.query(TABLE_NAME_GENRES, new String[]{KEY_ID},
                KEY_GENRES_NAME + "='" + cv.getAsString(KEY_GENRES_NAME) + "'",
                null, null, null, null);
        long result = -1;
        if (c.getCount() > 0 && c.moveToFirst()) {
            result = c.getLong(c.getColumnIndex(KEY_ID));
        }
        c.close();
        if (result == -1)
            result = db.insert(TABLE_NAME_GENRES, null, cv);
        return result;
    }

    public long addGenre(Genre g) {
        return addGenre(g.toContentValues());
    }

    public long addPlaylist(Playlist p) {
        return addPlaylist(p.toContentValues());
    }

    public long addPlaylist(ContentValues cv) {
        return db.insert(TABLE_NAME_PLAYLISTS, null, cv);
    }

    public long addTrack(Track t) {
        return addTrack(t.toContentValues());
    }

    public long addTrack(ContentValues cv) {
        long result = db.insert(TABLE_NAME_TRACKS, null, cv);
        for (String g : cv.getAsString(KEY_TRACKS_GENRES).split(", ")) {
            addGenreToTrack(result, addGenre(new Genre(g)));
        }
        return result;
    }

    private boolean addGenreToTrack(long trackId, long genreId) {
        ContentValues values = new ContentValues();
        values.put(KEY_TRACKS_GENRES_TRACK_ID, trackId);
        values.put(KEY_TRACKS_GENRES_GENRE_ID, genreId);
        return db.insert(TABLE_NAME_TRACKS_GENRES, null, values) > 0;
    }

    public boolean addTrackToPlaylist(long trackId, long playlistId) {
        ContentValues values = new ContentValues();
        values.put(KEY_TRACKS_PLAYLISTS_TRACK_ID, trackId);
        values.put(KEY_TRACKS_PLAYLISTS_PLAYLIST_ID, playlistId);
        return db.insert(TABLE_NAME_TRACKS_PLAYLISTS, null, values) > 0;
    }

    public boolean deleteTrackById(long trackId) {
        return deleteTracks(KEY_ID + "='" + trackId+ "'") == 1;
    }

    public int deleteTracks(String where) {
        return db.delete(TABLE_NAME_TRACKS, where, null);
    }
}

