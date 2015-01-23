package ru.ifmo.md.exam1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.sql.SQLException;

/**
 * Created by Яна on 23.01.2015.
 */
public class SongsBase implements BaseColumns {
    public static final String TABLE_NAME = "songs";
    public static final String _ID = "_id";
    public static final String ARTIST = "artist";
    public static final String NAME = "name";
    public static final String DURATION = "duration";
    public static final String POPULARITY = "popularity";
    public static final String YEAR = "year";
    Context context;

    public static final String INIT =
            "create table if not exists " +
                    TABLE_NAME + "(" +
                    _ID + " integer primary key autoincrement, " +
                    ARTIST + " text, " +
                    NAME + " text, " +
                    DURATION + " text, " +
                    POPULARITY + " integer, " +
                    YEAR + " integer)";

    public static final String REMOVE =
            "drop table if exists " + TABLE_NAME;

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INIT);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(REMOVE);
        onCreate(sqLiteDatabase);
    }

    public SongsBase(Context context) {
        this.context = context;
    }

    DBHelper myDBHelper;
    SQLiteDatabase myDB;

    public SongsBase open() throws SQLException {
        myDBHelper = new DBHelper(context);
        myDB = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        myDB.close();
        myDBHelper.close();
    }

}
