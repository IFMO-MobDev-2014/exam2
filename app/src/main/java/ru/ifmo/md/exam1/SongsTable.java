package ru.ifmo.md.exam1;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by timur on 23.01.15.
 */
public class SongsTable {
    public static final String TABLE_NAME = "songs";
    public static final String DATABASE_DESTROY = "DROP TABLE " + TABLE_NAME + ";";
    public static final String SONG_NAME = "name";
    public static final String SONG_AUTHOR = "author";
    public static final String SONG_POPULARITY = "popularity";
    public static final String SONG_JANRES = "janres";
    public static final String SONG_YEAR = "year";
    public static final String SONG_ID = "id";
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            SONG_ID + " INTEGER NOT NULL UNIQUE, \n" +
            SONG_NAME + " TEXT NOT NULL, \n" +
            SONG_AUTHOR + " TEXT NOT NULL, \n" +
            SONG_POPULARITY + " TEXT NOT NULL, \n" +
            SONG_JANRES + " TEXT NOT NULL, \n" +
            SONG_YEAR + " TEXT NOT NULL\n" +
            ");";

    public SongsTable(SQLiteDatabase sqLiteDatabase) {
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
