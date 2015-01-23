package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by timur on 23.01.15.
 */
public class PlaylistsTable {
    public static final String TABLE_NAME = "feeds";
    public static final String DATABASE_DESTROY = "DROP TABLE " + TABLE_NAME + ";";
    public static final String COLUMN_ROWID_QUERY = "rowid as _id";
    public static final String COLUMN_ROWID = "rowid";
    public static final String COLUMN_ROWID_AFTER_QUERY = "_id";
    public static final String PLAYLIST_NAME = "name";
    public static final String PLAYLIST_AUTHORS = "authors";
    public static final String PLAYLIST_YEARS = "years";
    public static final String PLAYLIST_JANRES = "janres";
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            COLUMN_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PLAYLIST_NAME + " TEXT NOT NULL UNIQUE, " +
            PLAYLIST_AUTHORS + " TEXT NOT NULL, " +
            PLAYLIST_JANRES + " TEXT NOT NULL, " +
            PLAYLIST_YEARS + " TEXT NOT NULL " +
            ");";
    
    public PlaylistsTable(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }
    
    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL(DATABASE_DESTROY);
            onCreate(sqLiteDatabase);
        }
    }
}
