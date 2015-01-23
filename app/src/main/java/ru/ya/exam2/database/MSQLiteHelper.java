package ru.ya.exam2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vanya on 21.01.15.
 */
public class MSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_A = "A";
    public static final String TABLE_B = "B";
    public static final String TABLE_C = "C";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "TEXTT";
    public static final String COLUMN_DATE = "DATEE";

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";


    public MSQLiteHelper(Context context) {
        super(context, "MyBase", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_A + " ("
                + COLUMN_TEXT + " text, "
                + COLUMN_DATE + " text, "
                + COLUMN_ID + " integer primary key autoincrement)");
        db.execSQL("CREATE TABLE " + TABLE_B + " ("
                + COLUMN_TEXT + " text, "
                + COLUMN_ID + " integer primary key autoincrement)");

        db.execSQL("CREATE TABLE " + TABLE_C + " ("
                + COLUMN_TEXT + " text, "
                + COLUMN_ID + " integer primary key autoincrement)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL("DROP TABLE " + TABLE_A);
            db.execSQL("DROP TABLE " + TABLE_B);
            db.execSQL("DROP TABLE " + TABLE_C);
            onCreate(db);
        }
    }
}
