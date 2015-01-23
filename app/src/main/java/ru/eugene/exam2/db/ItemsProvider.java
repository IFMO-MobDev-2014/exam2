package ru.eugene.exam2.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ru.eugene.exam2.items.Item1Source;
import ru.eugene.exam2.items.Item2Source;

/**
 * Created by eugene on 1/21/15.
 */
public class ItemsProvider extends ContentProvider {
    private static final String AUTHORITY = "ru.eugene.exam2.db";
    private static final String PATH_ITEM1 = "item1";
    private static final String PATH_ITEM2 = "item2";

    public static final Uri CONTENT_URI_ITEM1 = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_ITEM1);

    public static final Uri CONTENT_URI_ITEM2 = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_ITEM2);

    private static final int ITEM1 = 10;
    private static final int ITEM2 = 30;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, PATH_ITEM1, ITEM1);
        sURIMatcher.addURI(AUTHORITY, PATH_ITEM2, ITEM2);
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
            case ITEM1:
                return Item1Source.TABLE_NAME;
            case ITEM2:
                return Item2Source.TABLE_NAME;
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(getTableName(uri), null, values);
        if (id > 0) {
            uri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri;
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
