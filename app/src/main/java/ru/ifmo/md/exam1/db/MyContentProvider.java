package ru.ifmo.md.exam1.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Яна on 23.01.2015.
 */
public class MyContentProvider extends ContentProvider {
    public static final String SONGS_BASE_NAME = "songs";
    public static final String PLAY_BASE_NAME = "playlists";
    public static final String AUTHORITY = "ru.ifmo.md.exam1";
    public static final String SONGS = "songs";
    public static final String PLAY = "play";
    public static final int URITYPE_SONGS = 1;
    public static final int URITYPE_PLAY = 2;
    public static final int URITYPE_SONG_INFO = 3;
    public static final int URITYPE_PLAY_INFO = 4;

    public static final Uri QUERY = Uri.parse("content://" + AUTHORITY + "/" + SONGS);
    public static final Uri QUERY_PLAY = Uri.parse("content://" + AUTHORITY + "/" + PLAY);

    public DBHelper myDBHelper;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, SONGS, URITYPE_SONGS);
        uriMatcher.addURI(AUTHORITY, SONGS + "/#", URITYPE_SONG_INFO);
        uriMatcher.addURI(AUTHORITY, PLAY, URITYPE_PLAY);
        uriMatcher.addURI(AUTHORITY, PLAY + "/#", URITYPE_PLAY_INFO);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        myDBHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        SQLiteDatabase myDB = myDBHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case URITYPE_SONGS:
                queryBuilder.setTables(SONGS_BASE_NAME);
                break;
            case URITYPE_PLAY:
                queryBuilder.setTables(PLAY_BASE_NAME);
                break;
            case URITYPE_SONG_INFO:
                queryBuilder.setTables(SONGS_BASE_NAME);
                queryBuilder.appendWhere(SongsBase._ID + "=" + uri.getLastPathSegment());
                break;
            case URITYPE_PLAY_INFO:
                queryBuilder.setTables(PLAY_BASE_NAME);
                queryBuilder.appendWhere(PlayBase._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong arguments in query");
        }
        return queryBuilder.query(myDB, strings, s, strings2, null, null, s2);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase myDB = myDBHelper.getWritableDatabase();
        long id;
        switch(uriMatcher.match(uri)) {
            case URITYPE_SONGS:
                id = myDB.insert(SongsBase.TABLE_NAME, null, contentValues);
                break;
            case URITYPE_PLAY:
                id = myDB.insert(PlayBase.TABLE_NAME, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Wrong arguments in insert");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        /*SQLiteDatabase myDB = myDBHelper.getWritableDatabase();
        int removed;
        switch(uriMatcher.match(uri)) {
            case URITYPE_VALUE_DATA:
                removed = myDB.delete(ValuesBase.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Wrong arguments in delete");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return removed;*/
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase myDB = myDBHelper.getWritableDatabase();
        int updated;
        String name;
        switch(uriMatcher.match(uri)) {
            case URITYPE_SONG_INFO:
                name = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    updated = myDB.update(SongsBase.TABLE_NAME, contentValues,
                            SongsBase._ID + " = " + name,strings);
                } else {
                    updated = myDB.update(SongsBase.TABLE_NAME, contentValues,
                            SongsBase._ID + " = " + name + " and " + s, strings);
                }
                break;
            case URITYPE_PLAY_INFO:
                name = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    updated = myDB.update(PlayBase.TABLE_NAME, contentValues,
                            PlayBase._ID + " = " + name,strings);
                } else {
                    updated = myDB.update(PlayBase.TABLE_NAME, contentValues,
                            PlayBase._ID + " = " + name + " and " + s, strings);
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong arguments in delete");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updated;
    }
}

