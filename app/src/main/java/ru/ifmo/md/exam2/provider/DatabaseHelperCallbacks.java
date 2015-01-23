/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.ifmo.md.exam2.BuildConfig;
import ru.ifmo.md.exam2.Track;
import ru.ifmo.md.exam2.TracksParser;
import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistContentValues;
import ru.ifmo.md.exam2.provider.playlist.PlaylistCursor;
import ru.ifmo.md.exam2.provider.playlist.PlaylistSelection;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackColumns;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackContentValues;
import ru.ifmo.md.exam2.provider.track.TrackColumns;
import ru.ifmo.md.exam2.provider.track.TrackCursor;
import ru.ifmo.md.exam2.provider.track.TrackSelection;

/**
 * Implement your custom database creation or upgrade code here.
 * <p/>
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class DatabaseHelperCallbacks {
    private static final String TAG = DatabaseHelperCallbacks.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");

        PlaylistContentValues contentValues = new PlaylistContentValues();
        contentValues.putName("All");
        db.insert(PlaylistColumns.TABLE_NAME, null, contentValues.values());

        PlaylistSelection playlistSel = new PlaylistSelection()
                .name("All");
        Cursor basePlaylistCursor = db.query(PlaylistColumns.TABLE_NAME, null,
                playlistSel.sel(), playlistSel.args(), null, null, null);
        basePlaylistCursor.moveToFirst();
        PlaylistCursor playlistCursor = new PlaylistCursor(basePlaylistCursor);
        long playlistId = playlistCursor.getId();

        AssetManager assetManager = null;
        try {
            assetManager = context.getAssets();
            db.beginTransaction();
            InputStream is = assetManager.open("music.txt");
            List<Track> tracks = TracksParser.parse(is);
            for (Track track : tracks) {
                db.insert(TrackColumns.TABLE_NAME, null, track.toContentValues().values());
            }
            db.setTransactionSuccessful();
        } catch (IOException | JSONException e) {
            Log.d("ParseAssets", e.getMessage());
        } finally {
            db.endTransaction();
            if (assetManager != null) {
                assetManager.close();
            }
        }

        TrackSelection query = new TrackSelection();
        Cursor baseTrackCursor = db.query(TrackColumns.TABLE_NAME,
                null,
                query.sel(), query.args(), null, null, null);
        baseTrackCursor.moveToFirst();
        TrackCursor cursor = new TrackCursor(
                baseTrackCursor);
        try {
            db.beginTransaction();
            if (cursor.moveToFirst()) {
                do {
                    long trackId = cursor.getId();
                    ContentValues values = new PlaylistToTrackContentValues()
                            .putPlaylistId(playlistId)
                            .putTrackId(trackId).values();
                    db.insert(PlaylistToTrackColumns.TABLE_NAME, null, values);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
    }
}
