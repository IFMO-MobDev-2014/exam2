package ru.ifmo.md.exam1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.sql.SQLException;

/**
 * Created by Яна on 23.01.2015.
 */
public class PlayBase implements BaseColumns {
    public static final String TABLE_NAME = "playlists";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String SONGS = "songs";
    Context context;

    public static final String INIT =
            "create table if not exists " +
                    TABLE_NAME + "(" +
                    _ID + " integer primary key autoincrement, " +
                    NAME + " text, " +
                    SONGS + " text)";

    public static final String REMOVE =
            "drop table if exists " + TABLE_NAME;

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INIT);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(REMOVE);
        onCreate(sqLiteDatabase);
    }

    public PlayBase(Context context) {
        this.context = context;
    }

    DBHelper myDBHelper;
    SQLiteDatabase myDB;

    public PlayBase open() throws SQLException {
        myDBHelper = new DBHelper(context);
        myDB = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        myDB.close();
        myDBHelper.close();
    }
}
