package ru.ifmo.md.exam1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME =  "dataBase";
    public static final String SONGS_TABLE = "songs";
    public static final String COLUMN_ID = "_id";
    public static final String ARTIST = "artist";
    public static final String NAME = "name";
    public static final String GENRES = "genres";
    public static final String POPULARITY = "popularity";
    public static final String YEAR = "year";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_IMAGES_BASE = "create table " + SONGS_TABLE + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + ARTIST + " TEXT, "
            + NAME + " TEXT, "
            + GENRES + " TEXT, "
            + POPULARITY + " INTEGER, "
            + YEAR + " INTEGER" + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL(CREATE_IMAGES_BASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        dataBase.execSQL("drop table if exists " + SONGS_TABLE);
        onCreate(dataBase);
    }
}
