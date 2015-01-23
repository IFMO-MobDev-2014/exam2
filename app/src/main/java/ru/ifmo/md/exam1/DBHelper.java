package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.io.File;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "mydb";
    public static final String TRACKS_TABLE = "tracks";
    public static final Integer VERSION = 7;

    public static final String TRACK_KEY_NAME = "name";
    public static final String TRACK_KEY_ARTIST = "artist";
    public static final String TRACK_KEY_URL = "url";
    public static final String TRACK_KEY_DURATION = "duration";
    public static final String TRACK_KEY_POPULARITY = "popularity";
    public static final String TRACK_KEY_YEAR = "year";
    public static final String KEY_RAW_TYPE = "type";
    public static final String KEY_LIST = "list";


    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TRACKS_TABLE + " ("
                + "_id integer primary key autoincrement,"
                + TRACK_KEY_NAME + " text,"
                + TRACK_KEY_ARTIST + " text,"
                + TRACK_KEY_URL + " text,"
                + TRACK_KEY_DURATION + " text,"
                + TRACK_KEY_POPULARITY + " integer,"
                + KEY_RAW_TYPE + " integer,"
                + KEY_LIST + " text,"
                + TRACK_KEY_YEAR + " integer" + ");");

        ContentValues cv = new ContentValues();
        cv.put(KEY_RAW_TYPE,2);
        cv.put(TRACK_KEY_NAME, "All Tracks");
        db.insert(TRACKS_TABLE,null,cv);


        //JSONObject
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TRACKS_TABLE);
        onCreate(db);
    }
}