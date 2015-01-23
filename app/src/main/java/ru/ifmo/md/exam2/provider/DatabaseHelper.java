/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import ru.ifmo.md.exam2.BuildConfig;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.track.TrackColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "exam2.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;
    private final Context mContext;
    private final DatabaseHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_PLAYLIST_TO_TRACK = "CREATE TABLE IF NOT EXISTS "
            + PlaylistToTrackColumns.TABLE_NAME + " ( "
            + PlaylistToTrackColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PlaylistToTrackColumns.PLAYLIST_ID + " INTEGER NOT NULL, "
            + PlaylistToTrackColumns.TRACK_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_playlist_id FOREIGN KEY (" + PlaylistToTrackColumns.PLAYLIST_ID + ") REFERENCES playlist (_id) ON DELETE CASCADE"
            + " );";

    private static final String SQL_CREATE_TABLE_PLAYLIST = "CREATE TABLE IF NOT EXISTS "
            + PlaylistColumns.TABLE_NAME + " ( "
            + PlaylistColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PlaylistColumns.NAME + " TEXT NOT NULL "
            + " );";

    private static final String SQL_CREATE_INDEX_PLAYLIST_NAME = "CREATE INDEX IDX_PLAYLIST_NAME "
            + " ON " + PlaylistColumns.TABLE_NAME + " ( " + PlaylistColumns.NAME + " );";

    private static final String SQL_CREATE_TABLE_TRACK = "CREATE TABLE IF NOT EXISTS "
            + TrackColumns.TABLE_NAME + " ( "
            + TrackColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrackColumns.TITLE + " TEXT NOT NULL, "
            + TrackColumns.AUTHOR + " TEXT NOT NULL, "
            + TrackColumns.YEAR + " INTEGER NOT NULL, "
            + TrackColumns.GENRES_MASK + " INTEGER NOT NULL "
            + " );";

    private static final String SQL_CREATE_INDEX_TRACK_TITLE = "CREATE INDEX IDX_TRACK_TITLE "
            + " ON " + TrackColumns.TABLE_NAME + " ( " + TrackColumns.TITLE + " );";

    private static final String SQL_CREATE_INDEX_TRACK_AUTHOR = "CREATE INDEX IDX_TRACK_AUTHOR "
            + " ON " + TrackColumns.TABLE_NAME + " ( " + TrackColumns.AUTHOR + " );";

    private static final String SQL_CREATE_INDEX_TRACK_YEAR = "CREATE INDEX IDX_TRACK_YEAR "
            + " ON " + TrackColumns.TABLE_NAME + " ( " + TrackColumns.YEAR + " );";

    // @formatter:on

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static DatabaseHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static DatabaseHelper newInstancePreHoneycomb(Context context) {
        return new DatabaseHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new DatabaseHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static DatabaseHelper newInstancePostHoneycomb(Context context) {
        return new DatabaseHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private DatabaseHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new DatabaseHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_PLAYLIST_TO_TRACK);
        db.execSQL(SQL_CREATE_TABLE_PLAYLIST);
        db.execSQL(SQL_CREATE_INDEX_PLAYLIST_NAME);
        db.execSQL(SQL_CREATE_TABLE_TRACK);
        db.execSQL(SQL_CREATE_INDEX_TRACK_TITLE);
        db.execSQL(SQL_CREATE_INDEX_TRACK_AUTHOR);
        db.execSQL(SQL_CREATE_INDEX_TRACK_YEAR);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
