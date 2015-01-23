package ru.ifmo.md.exam1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by german on 22.01.15.
 */
public class MyProvider extends ContentProvider {

    private SQLiteDatabase db;
    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;

    private static final String SONGS_TABLE = "songs";
    public static final String SONG_ID = "_id";
    public static final String SONG_ARTIST = "artist";
    public static final String SONG_NAME = "name";
    public static final String SONG_YEAR = "year";
    public static final String SONG_POP = "pop";
    public static final String SONG_URL = "url";
    public static final String SONG_DUR = "dur";
    private static final String SONG_TABLE_CREATE = "create table " + SONGS_TABLE + " ("
            + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SONG_ARTIST + " TEXT, "
            + SONG_NAME + " TEXT, "
            + SONG_YEAR + " INTEGER, "
            + SONG_POP + " INTEGER, "
            + SONG_URL + " TEXT, "
            + SONG_DUR + " TEXT"
            + ");";

    private static final String PLAYLISTS_TABLE = "playlists";
    public static final String PLAYLISTS_ID = "_id";
    public static final String PLAYLISTS_NAME = "name";
    private static final String PLAYLISTS_TABLE_CREATE = "create table " + PLAYLISTS_TABLE + " ("
            + PLAYLISTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLAYLISTS_NAME + " TEXT"
            + ");";

    // id of song -> id of playlist
    private static final String PLAYLIST_TABLE = "playlist";
    public static final String PLAYLIST_ID = "_id";
    public static final String PLAYLIST_PL_ID = "pl_id";
    public static final String PLAYLIST_SONG_ID = "song_id";
    private static final String PLAYLIST_TABLE_CREATE = "create table " + PLAYLIST_TABLE + " ("
            + PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLAYLIST_PL_ID + " INTEGER, "
            + PLAYLIST_SONG_ID + " INTEGER"
            + ");";

    /**
     * Provider constants
     */
    public static final String AUTHORITY = "ru.ifmo.md.exam1";

    public static final Uri DATA_CONTENT = Uri.parse("content://" + AUTHORITY);
    public static final Uri SONGS_CONTENT = Uri.parse("content://" + AUTHORITY +
            "/" + SONGS_TABLE);
    public static final Uri PLAYLISTS_CONTENT = Uri.parse("content://" + AUTHORITY +
            "/" + PLAYLISTS_TABLE);
    public static final Uri PLAYLIST_CONTENT = Uri.parse("content://" + AUTHORITY +
            "/" + PLAYLIST_TABLE);


    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SONG_TABLE_CREATE);
            db.execSQL(PLAYLISTS_TABLE_CREATE);
            db.execSQL(PLAYLIST_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private static final int uSONGS = 1;
    private static final int uSONGS_ID = 2;
    private static final int uPLAYLISTS = 3;
    private static final int uPLAYLISTS_ID = 4;
    private static final int uPLAYLIST = 5;
    private static final int uPLAYLIST_ID = 6;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, SONGS_TABLE, 1);
        uriMatcher.addURI(AUTHORITY, SONGS_TABLE + "/#", 2);
        uriMatcher.addURI(AUTHORITY, PLAYLISTS_TABLE, 3);
        uriMatcher.addURI(AUTHORITY, PLAYLISTS_TABLE + "/#", 4);
        uriMatcher.addURI(AUTHORITY, PLAYLIST_TABLE, 5);
        uriMatcher.addURI(AUTHORITY, PLAYLIST_TABLE + "/#", 6);
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case uSONGS:
                qb.setTables(SONGS_TABLE);
                break;
            case uSONGS_ID:
                qb.setTables(SONGS_TABLE);
                qb.appendWhere(SONG_ID + "=" + uri.getPathSegments().get(1));
                break;
            case uPLAYLISTS:
                qb.setTables(PLAYLISTS_TABLE);
                break;
            case uPLAYLISTS_ID:
                qb.setTables(PLAYLISTS_TABLE);
                qb.appendWhere(PLAYLISTS_ID + "=" + uri.getPathSegments().get(1));
                break;
            case uPLAYLIST:
                qb.setTables(PLAYLIST_TABLE);
                break;
            case uPLAYLIST_ID:
                qb.setTables(PLAYLIST_TABLE);
                qb.appendWhere(PLAYLIST_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case uSONGS:
                long rowID = db.insert(SONGS_TABLE, null, values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(SONGS_CONTENT, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("City inserting error: Failed insert values to " + uri);
            case uPLAYLISTS:
                rowID = db.insert(PLAYLISTS_TABLE, null, values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(PLAYLISTS_CONTENT, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("City inserting error: Failed insert values to " + uri);
            case uPLAYLIST:
                rowID = db.insert(PLAYLIST_TABLE, null, values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(PLAYLIST_CONTENT, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                throw new SQLException("City inserting error: Failed insert values to " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
