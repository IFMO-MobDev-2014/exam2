package ru.eugene.exam2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.eugene.exam2.items.Item1Source;
import ru.eugene.exam2.items.Item2Source;

/**
 * Created by eugene on 1/21/15.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String NAME = "items.db";
    public static final int VERSION = 3;

    public MyOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Item1Source.CREATE_TABLE);
        db.execSQL(Item2Source.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Item1Source.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Item2Source.TABLE_NAME);
        onCreate(db);
    }
}
