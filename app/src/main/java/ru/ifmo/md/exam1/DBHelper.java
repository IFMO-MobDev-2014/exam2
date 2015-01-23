package ru.ifmo.md.exam1;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Амир on 23.01.2015.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final int DATABASE_VERSION = 1;

    public static final String PLAYLISTS_TABLE_NAME = "PLAYLISTS";
    public static final String PLAYLISTS_COLUMN_ID = "ID";
    public static final String PLAYLISTS_COLUMN_NAME = "NAME";
    public static final String PLAYLISTS_COLUMN_ARTIST = "ARTIST";
    public static final String PLAYLISTS_COLUMN_YEAR = "YEAR";
    public static final String PLAYLISTS_ON_CREATE = "create table if not exists " + PLAYLISTS_TABLE_NAME + "("
            + PLAYLISTS_COLUMN_ID + " integer primary key autoincrement, " + PLAYLISTS_COLUMN_ARTIST + " text, " + PLAYLISTS_COLUMN_NAME + " text);";
    public static final String PLAYLISTS_ON_DESTROY = "drop table if exists " + PLAYLISTS_TABLE_NAME;


    public static final String SONGSINPLAYLIST_TABLE_NAME = "SONGSINPLAYLISTS";
    public static final String SONGSINPLAYLIST_COLUMN_ID = "ID";
    public static final String SONGSINPLAYLIST_COLUMN_SONGID = "SONG";
    public static final String SONGSINPLAYLIST_COLUMN_PLAYLISTID = "PLAYLIST";
    public static final String SONGSINPLAYLIST_ON_CREATE = "create table if not exists " + SONGSINPLAYLIST_TABLE_NAME + "("
            + SONGSINPLAYLIST_COLUMN_ID + " integer primary key autoincrement, " + SONGSINPLAYLIST_COLUMN_PLAYLISTID
            + " integer, " + SONGSINPLAYLIST_COLUMN_SONGID + " integer);";
    public static final String SONGSINPLAYLIST_ON_DESTROY = "drop table if exists " + SONGSINPLAYLIST_TABLE_NAME;

    public static final String SONGS_TABLE_NAME = "SONGS";
    public static final String SONGS_COLUMN_ID = "ID";
    public static final String SONGS_COLUMN_NAME = "NAME";
    public static final String SONGS_COLUMN_ARTIST = "ARTIST";
    public static final String SONGS_COLUMN_YEAR = "YEAR";
    public static final String SONGS_COLUMN_GENRES = "GENRES";
    public static final String SONGS_ON_CREATE = "create table if not exists " + SONGS_TABLE_NAME + "("
            + SONGS_COLUMN_ID + " integer primary key autoincrement, " + SONGS_COLUMN_NAME + " text, "
            + SONGS_COLUMN_ARTIST + " text, " + SONGS_COLUMN_YEAR + " integer, " + SONGS_COLUMN_GENRES + " text);";
    public static final String SONGS_ON_DESTROY = "drop table if exists " + SONGS_TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PLAYLISTS_ON_CREATE);
        sqLiteDatabase.execSQL(SONGS_ON_CREATE);
        sqLiteDatabase.execSQL(SONGSINPLAYLIST_ON_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(PLAYLISTS_ON_DESTROY);
        sqLiteDatabase.execSQL(SONGS_ON_DESTROY);
        sqLiteDatabase.execSQL(SONGSINPLAYLIST_ON_DESTROY);
        onCreate(sqLiteDatabase);
    }
}
