package ru.ifmo.md.exam1.database.song;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vadim on 23/01/15.
 */
public class SongDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = SongDatabaseHelper.class.getName();

    public static final String SONG_TABLE = "song_table";

    public static final String SONG_DATABASE_NAME = "song.db";
    public static final int SONG_DATABASE_VERSION = 1;

    public SongDatabaseHelper(Context context) {
        super(context, SONG_DATABASE_NAME, null, SONG_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createCommand = "CREATE TABLE " /*+ "IF NOT EXISTS "*/ + SONG_TABLE + "(" +
                SongContract.Song._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SongContract.Song.ID + " TEXT NOT NULL, " +
                SongContract.Song.NAME + " TEXT NOT NULL, " +
                SongContract.Song.URL + " TEXT, " +
                SongContract.Song.DURATION + " INTEGER, " +
                SongContract.Song.POPULARITY + " INTEGER, " +
                SongContract.Song.YEAR + " INTEGER, " +
                SongContract.Song.GENRES + " TEXT NOT NULL, " +
                SongContract.Song.VALID_STATE + " INTEGER CHECK(" +
                    SongContract.Song.VALID_STATE + " IN (0, 1))" +
                ");";
        Log.d(TAG, "execSQL( " + createCommand + " )");
        db.execSQL(createCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,
                "Upgrading database from version " + oldVersion +
                        " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SONG_TABLE);
        onCreate(db);
    }
}
