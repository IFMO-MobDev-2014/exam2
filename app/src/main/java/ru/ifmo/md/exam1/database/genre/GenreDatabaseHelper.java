package ru.ifmo.md.exam1.database.genre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vadim on 23/01/15.
 */
public class GenreDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = GenreDatabaseHelper.class.getName();

    public static final String GENRE_TABLE = "genre_table";

    public static final String SONG_DATABASE_NAME = "genre.db";
    public static final int SONG_DATABASE_VERSION = 1;

    public GenreDatabaseHelper(Context context) {
        super(context, SONG_DATABASE_NAME, null, SONG_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createCommand = "CREATE TABLE " /*+ "IF NOT EXISTS "*/ + GENRE_TABLE + "(" +
                GenreContract.Genre._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GenreContract.Genre.ID + " TEXT NOT NULL, " +
                GenreContract.Genre.NAME + " TEXT NOT NULL, " +
                GenreContract.Genre.VALID_STATE + " INTEGER CHECK(" +
                    GenreContract.Genre.VALID_STATE + " IN (0, 1))" +
                ");";
        Log.d(TAG, "execSQL( " + createCommand + " )");
        db.execSQL(createCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,
                "Upgrading database from version " + oldVersion +
                        " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + GENRE_TABLE);
        onCreate(db);
    }
}
