package ru.ifmo.md.exam1.database.playlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vadim on 23/01/15.
 */
public class PlaylistDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = PlaylistDatabaseHelper.class.getName();

    public static final String PLAYLIST_TABLE = "playlist_table";

    public static final String PLAYLIST_DATABASE_NAME = "playlist.db";
    public static final int PLAYLIST_DATABASE_VERSION = 1;

    public PlaylistDatabaseHelper(Context context) {
        super(context, PLAYLIST_DATABASE_NAME, null, PLAYLIST_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createCommand = "CREATE TABLE " /*+ "IF NOT EXISTS "*/ + PLAYLIST_TABLE + "(" +
                PlaylistContract.Playlist._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PlaylistContract.Playlist.ID + " TEXT NOT NULL, " +
                PlaylistContract.Playlist.NAME + " TEXT NOT NULL, " +
                PlaylistContract.Playlist.SONGS + "TEXT NOT NULL, " +
                PlaylistContract.Playlist.VALID_STATE + " INTEGER CHECK(" +
                    PlaylistContract.Playlist.VALID_STATE + " IN (0, 1))" +
                ");";
        Log.d(TAG, "execSQL( " + createCommand + " )");
        db.execSQL(createCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,
                "Upgrading database from version " + oldVersion +
                        " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_TABLE);
        onCreate(db);
    }
}
