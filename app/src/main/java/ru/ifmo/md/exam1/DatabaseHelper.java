package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by timur on 08.01.15.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 10;
    private PlaylistsTable citiesTable;
    private SongsTable weatherTable;

    public DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        citiesTable = new PlaylistsTable(sqLiteDatabase);
        weatherTable = new SongsTable(sqLiteDatabase);
        ContentValues contentValues = new ContentValues();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        citiesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        weatherTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

}
