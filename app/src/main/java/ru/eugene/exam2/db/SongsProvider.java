package ru.eugene.exam2.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ru.eugene.exam2.items.PlayListsSource;
import ru.eugene.exam2.items.SongsSource;

/**
 * Created by eugene on 1/21/15.
 */
public class SongsProvider extends ContentProvider {
    private static final String AUTHORITY = "ru.eugene.exam2.db";
    private static final String PATH_SONG = "song";
    private static final String PATH_PLAY_LIST = "play_list";

    public static final Uri CONTENT_URI_SONG = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_SONG);

    public static final Uri CONTENT_URI_PLAY_LIST = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_PLAY_LIST);

    private static final int SONG = 10;
    private static final int PLAY_LIST = 30;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, PATH_SONG, SONG);
        sURIMatcher.addURI(AUTHORITY, PATH_PLAY_LIST, PLAY_LIST);
    }

    private MyOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MyOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor result = db.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);

        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    private String getTableName(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case SONG:
                return SongsSource.TABLE_NAME;
            case PLAY_LIST:
                Log.e("LOG", "getTable: PlayList");
                return PlayListsSource.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e("LOG", "insert: " + getTableName(uri));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(getTableName(uri), null, values);
        if (id > 0) {
            uri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        int ans = 0;
        for (ContentValues value : values) {
            long id = db.insert(getTableName(uri), null, value);
            if (id > 0) {
                ans++;
            }
        }

        if (ans > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return ans;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int id = db.delete(getTableName(uri), selection, selectionArgs);
        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int id = db.update(getTableName(uri), values, selection, selectionArgs);
        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return id;
    }
}
