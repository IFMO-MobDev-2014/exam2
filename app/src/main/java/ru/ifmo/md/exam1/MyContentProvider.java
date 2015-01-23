package ru.ifmo.md.exam1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Алексей on 23.01.2015.
 */
public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = MyContentProvider.class.getName();

    public static final Uri SONG_CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY + "/" + SongsDBHelper.SONG_TABLE_NAME);
    public static final Uri PLAYLIST_CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY + "/" + SongsDBHelper.PLAYLIST_TABLE_NAME);
    static final String SONG_CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + SongsDBHelper.SONG_TABLE_NAME;
    static final String PLAYLIST_CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + SongsDBHelper.PLAYLIST_TABLE_NAME;
    public static final int URI_SONG_ID = 0;
    public static final int URI_PLAYLIST_ID = 1;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, SongsDBHelper.SONG_TABLE_NAME, URI_SONG_ID);
        uriMatcher.addURI(AUTHORITY, SongsDBHelper.PLAYLIST_TABLE_NAME, URI_PLAYLIST_ID);
    }

    private SongsDBHelper songsDbHelper;


    @Override
    public boolean onCreate() {
        songsDbHelper = new SongsDBHelper(getContext());
        try {
            getContext().getAssets().open("music.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Log.d("query", "start");
        switch (uriMatcher.match(uri)) {
            case URI_SONG_ID:
                Cursor cursor = songsDbHelper.getReadableDatabase().query(SongsDBHelper.SONG_TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), SONG_CONTENT_URI);
                return cursor;
            case URI_PLAYLIST_ID:
                cursor = songsDbHelper.getReadableDatabase().query(SongsDBHelper.PLAYLIST_TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), PLAYLIST_CONTENT_URI);
                return cursor;
            default:
                throw new IllegalArgumentException("wrong URI");
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_SONG_ID:
                return SONG_CONTENT_TYPE;
            case URI_PLAYLIST_ID:
                return PLAYLIST_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("wrong URI");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case URI_SONG_ID:
                long id = songsDbHelper.getWritableDatabase().insert(SongsDBHelper.SONG_TABLE_NAME, null, contentValues);
                Uri result = ContentUris.withAppendedId(SONG_CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(result, null);
                return result;
            case URI_PLAYLIST_ID:
                id = songsDbHelper.getWritableDatabase().insert(SongsDBHelper.PLAYLIST_TABLE_NAME, null, contentValues);
                result = ContentUris.withAppendedId(PLAYLIST_CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(result, null);
                return result;
            default:
                throw new IllegalArgumentException("wrong URI");
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case URI_SONG_ID:
                int count = songsDbHelper.getWritableDatabase().delete(SongsDBHelper.SONG_TABLE_NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            case URI_PLAYLIST_ID:
                count = songsDbHelper.getWritableDatabase().delete(SongsDBHelper.PLAYLIST_TABLE_NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("wrong URI");
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case URI_SONG_ID:
                int count = songsDbHelper.getWritableDatabase().update(SongsDBHelper.SONG_TABLE_NAME, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            case URI_PLAYLIST_ID:
                count = songsDbHelper.getWritableDatabase().update(SongsDBHelper.PLAYLIST_TABLE_NAME, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("wrong URI");
        }
    }
}
