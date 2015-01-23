package ru.ifmo.md.exam1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DatabaseContentProvider extends ContentProvider {
    private static final int PLAYLISTS_DIR = 1;
    private static final int SONGS_DIR = 2;

    private static final String AUTHORITY = "com.example.timur.rssreader";
    private static final String BASE_URI = "feeds";
    public static final Uri URI_FEED_DIR = Uri.parse("content://" + AUTHORITY + "/" + BASE_URI);

    private static final String UNSUPPORTED_OPERATION = "Unsupported operation type";
    private static final String ILLEGAL_ARGUMENT = "Invalid URI argument: ";

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_URI, PLAYLISTS_DIR);
        uriMatcher.addURI(AUTHORITY, BASE_URI + "/#", SONGS_DIR);
    }

    private DatabaseHelper databaseHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        if (uriType == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + uri.toString());
        } else if (uriType != SONGS_DIR) {
            throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
        }

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(PlaylistsTable.TABLE_NAME, "rowid = ?", new String[]{uri.getLastPathSegment()});
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        if (uriType == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + uri.toString());
        }

        String tableName = null;
        switch (uriType) {
            case PLAYLISTS_DIR:
                tableName = PlaylistsTable.TABLE_NAME;
                break;
            case SONGS_DIR:
                tableName = SongsTable.TABLE_NAME;
                break;
        }

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return uri.buildUpon().appendPath("" + sqLiteDatabase.insert(tableName, null, values)).build();
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext(), null);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int uriType = uriMatcher.match(uri);
        if (uriType == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + uri.toString());
        }

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        if (uriType == PLAYLISTS_DIR) {
            builder.setTables(PlaylistsTable.TABLE_NAME);
        } else {
            builder.setTables(SongsTable.TABLE_NAME);
            if (uriType == SONGS_DIR) {
                builder.appendWhere(SongsTable.SONG_ID + "=" + uri.getLastPathSegment());
            }
        }

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cr = builder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);

        cr.setNotificationUri(getContext().getContentResolver(), uri);

        return cr;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        if (uriType == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + uri.toString());
        } else if (uriType != SONGS_DIR) {
            throw new UnsupportedOperationException(UNSUPPORTED_OPERATION);
        }

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.update(PlaylistsTable.TABLE_NAME, values, "rowid = ?", new String[]{uri.getLastPathSegment()});
    }

}
