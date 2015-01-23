package ru.ya.exam2.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by vanya on 21.01.15.
 */
public class MContentProvider extends ContentProvider {
    public static final String AUTHORITY = "ru.ya.exam2.database.MContentProvider";

    public static final String PATH_A = "PA";
    public static final String PATH_B = "PB";
    public static final String PATH_C = "PC";

    public static final Uri URI_A = Uri.parse("content://" + AUTHORITY + "/" + PATH_A);
    public static final Uri URI_B = Uri.parse("content://" + AUTHORITY + "/" + PATH_B);
    public static final Uri URI_C = Uri.parse("content://" + AUTHORITY + "/" + PATH_C);


    public static final  UriMatcher uriMatcher = new UriMatcher(0);
    private MSQLiteHelper msqLiteHelper;

    static {
        uriMatcher.addURI(AUTHORITY, PATH_A, 1);
        uriMatcher.addURI(AUTHORITY, PATH_B, 2);
        uriMatcher.addURI(AUTHORITY, PATH_C, 3);
    }

    @Override
    public boolean onCreate() {
        msqLiteHelper = new MSQLiteHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case 1: cursor = msqLiteHelper.getReadableDatabase().query(MSQLiteHelper.TABLE_A, projection, selection, selectionArgs, sortOrder, null, null); break;
            case 2: cursor = msqLiteHelper.getReadableDatabase().query(MSQLiteHelper.TABLE_B, projection, selection, selectionArgs, sortOrder, null, null); break;
            case 3: cursor = msqLiteHelper.getReadableDatabase().query(MSQLiteHelper.TABLE_C, projection, selection, selectionArgs, sortOrder, null, null); break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uriItem = null;
        switch (uriMatcher.match(uri)) {
            case 1: uriItem = Uri.parse(PATH_A + "/" + msqLiteHelper.getWritableDatabase().insert(MSQLiteHelper.TABLE_A, null, values)); break;
            case 2: uriItem = Uri.parse(PATH_B + "/" + msqLiteHelper.getWritableDatabase().insert(MSQLiteHelper.TABLE_B, null, values)); break;
            case 3: uriItem = Uri.parse(PATH_C + "/" + msqLiteHelper.getWritableDatabase().insert(MSQLiteHelper.TABLE_C, null, values)); break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uriItem;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case 1: count = msqLiteHelper.getWritableDatabase().delete(MSQLiteHelper.TABLE_A, selection, selectionArgs); break;
            case 2: count = msqLiteHelper.getWritableDatabase().delete(MSQLiteHelper.TABLE_B, selection, selectionArgs); break;
            case 3: count = msqLiteHelper.getWritableDatabase().delete(MSQLiteHelper.TABLE_C, selection, selectionArgs); break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case 1: count = msqLiteHelper.getWritableDatabase().update(MSQLiteHelper.TABLE_A, values, selection, selectionArgs); break;
            case 2: count = msqLiteHelper.getWritableDatabase().update(MSQLiteHelper.TABLE_B, values, selection, selectionArgs); break;
            case 3: count = msqLiteHelper.getWritableDatabase().update(MSQLiteHelper.TABLE_C, values, selection, selectionArgs); break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
