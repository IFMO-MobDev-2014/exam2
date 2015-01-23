package ru.ifmo.md.exam1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

/**
 * Created by Яна on 23.01.2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Songs";
    public static final int DATABASE_VERSION = 3;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SongsBase.onCreate(db);
        PlayBase.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SongsBase.onUpgrade(db, oldVersion, newVersion);
        PlayBase.onUpgrade(db, oldVersion, newVersion);
    }
}
