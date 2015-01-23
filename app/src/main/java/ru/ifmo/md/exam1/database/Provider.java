package ru.ifmo.md.exam1.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam1.database.genre.GenreDatabaseHelper;
import ru.ifmo.md.exam1.database.playlist.PlaylistDatabaseHelper;
import ru.ifmo.md.exam1.database.song.SongDatabaseHelper;

/**
 * Created by vadim on 23/01/15.
 */
public class Provider extends ContentProvider {
    public static final String TAG = Provider.class.getSimpleName();

    public static final String AUTHORITY = Provider.class.getCanonicalName();

    public static final Uri PLAYLIST_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PlaylistDatabaseHelper.PLAYLIST_TABLE);
    public static final int URI_PLAYLIST_ID = 1;

    public static final Uri GENRE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GenreDatabaseHelper.GENRE_TABLE);
    public static final int URI_GENRE_ID = 2;

    public static final Uri SONG_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SongDatabaseHelper.SONG_TABLE);
    public static final int URI_SONG_ID = 3;

    private static final String PLAYLIST_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PlaylistDatabaseHelper.PLAYLIST_TABLE;
    private static final String GENRE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + GenreDatabaseHelper.GENRE_TABLE;
    private static final String SONG_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + SongDatabaseHelper.SONG_TABLE;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(AUTHORITY, PlaylistDatabaseHelper.PLAYLIST_TABLE, URI_PLAYLIST_ID);
        URI_MATCHER.addURI(AUTHORITY, GenreDatabaseHelper.GENRE_TABLE, URI_GENRE_ID);
        URI_MATCHER.addURI(AUTHORITY, SongDatabaseHelper.SONG_TABLE, URI_SONG_ID);
    }

    private PlaylistDatabaseHelper playlistDBHelper;
    private GenreDatabaseHelper genreDBHelper;
    private SongDatabaseHelper songDBHelper;

    @Override
    public boolean onCreate() {
        playlistDBHelper = new PlaylistDatabaseHelper(getContext());
        genreDBHelper = new GenreDatabaseHelper(getContext());
        songDBHelper = new SongDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        Cursor cursor;
        switch (match) {
            case URI_PLAYLIST_ID:
                cursor = playlistDBHelper.getReadableDatabase().query(PlaylistDatabaseHelper.PLAYLIST_TABLE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), PLAYLIST_CONTENT_URI);
                break;
            case URI_GENRE_ID:
                cursor = genreDBHelper.getReadableDatabase().query(GenreDatabaseHelper.GENRE_TABLE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), GENRE_CONTENT_URI);
                break;
            case URI_SONG_ID:
                cursor = songDBHelper.getReadableDatabase().query(SongDatabaseHelper.SONG_TABLE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), SONG_CONTENT_URI);
            default:
                throw new IllegalArgumentException("No such URI found: " + match);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_PLAYLIST_ID:
                return PLAYLIST_CONTENT_TYPE;
            case URI_GENRE_ID:
                return GENRE_CONTENT_TYPE;
            case URI_SONG_ID:
                return SONG_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("No such URI found: " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultURI;
        int match = URI_MATCHER.match(uri);
        long rowID;
        switch (match) {
            case URI_PLAYLIST_ID:
                rowID = playlistDBHelper.getWritableDatabase().insert(
                        PlaylistDatabaseHelper.PLAYLIST_TABLE, null, values);
                resultURI = ContentUris.withAppendedId(PLAYLIST_CONTENT_URI, rowID);
                break;
            case URI_GENRE_ID:
                rowID = genreDBHelper.getWritableDatabase().insert(
                        GenreDatabaseHelper.GENRE_TABLE, null, values);
                resultURI = ContentUris.withAppendedId(GENRE_CONTENT_URI, rowID);
                break;
            case URI_SONG_ID:
                rowID = songDBHelper.getWritableDatabase().insert(
                        SongDatabaseHelper.SONG_TABLE, null, values);
                resultURI = ContentUris.withAppendedId(SONG_CONTENT_URI, rowID);
                break;
            default:
                throw new IllegalArgumentException("No such URI found: " + match);
        }
        getContext().getContentResolver().notifyChange(resultURI, null);
        return resultURI;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result;
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_PLAYLIST_ID:
                result = playlistDBHelper.getWritableDatabase().delete(
                        PlaylistDatabaseHelper.PLAYLIST_TABLE, selection, selectionArgs);
                break;
            case URI_GENRE_ID:
                result = genreDBHelper.getWritableDatabase().delete(
                        GenreDatabaseHelper.GENRE_TABLE, selection, selectionArgs);
                break;
            case URI_SONG_ID:
                result = songDBHelper.getWritableDatabase().delete(
                        SongDatabaseHelper.SONG_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("No such URI found: " + match);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int result;
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_PLAYLIST_ID:
                result = playlistDBHelper.getWritableDatabase().update(
                        PlaylistDatabaseHelper.PLAYLIST_TABLE, values, selection, selectionArgs);
                break;
            case URI_GENRE_ID:
                result = genreDBHelper.getWritableDatabase().update(
                        GenreDatabaseHelper.GENRE_TABLE, values, selection, selectionArgs);
                break;
            case URI_SONG_ID:
                result = songDBHelper.getWritableDatabase().update(
                        SongDatabaseHelper.SONG_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("No such URI found: " + match);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}
