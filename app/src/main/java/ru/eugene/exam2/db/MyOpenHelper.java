package ru.eugene.exam2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.eugene.exam2.items.PlayListsSource;
import ru.eugene.exam2.items.SongsSource;

/**
 * Created by eugene on 1/21/15.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String NAME = "items.db";
    public static final int VERSION = 17;

    public MyOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SongsSource.CREATE_TABLE);
        db.execSQL(PlayListsSource.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SongsSource.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlayListsSource.TABLE_NAME);
        onCreate(db);
    }
}
