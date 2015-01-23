/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.ifmo.md.exam2.BuildConfig;
import ru.ifmo.md.exam2.Track;
import ru.ifmo.md.exam2.TracksParser;
import ru.ifmo.md.exam2.provider.playlist.PlaylistContentValues;
import ru.ifmo.md.exam2.provider.playlist.PlaylistSelection;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackContentValues;
import ru.ifmo.md.exam2.provider.track.TrackCursor;
import ru.ifmo.md.exam2.provider.track.TrackSelection;

/**
 * Implement your custom database creation or upgrade code here.
 *
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
        contentValues.insert(context.getContentResolver());

        long playlistId = new PlaylistSelection()
                .name("All")
                .query(context.getContentResolver())
                .getId();

        AssetManager assetManager = context.getAssets();
        try {
            db.beginTransaction();
            InputStream is = assetManager.open("music.txt");
            List<Track> tracks = TracksParser.parse(is);
            for (Track track : tracks) {
                track.toContentValues().insert(context.getContentResolver());
            }
            db.setTransactionSuccessful();
        } catch (IOException | JSONException e) {
            Log.d("ParseAssets", e.getMessage());
        } finally {
            db.endTransaction();
        }

        TrackCursor cursor = new TrackSelection().query(context.getContentResolver());
        try {
            db.beginTransaction();
            if (cursor.moveToFirst()) {
                do {
                    long trackId = cursor.getId();
                    new PlaylistToTrackContentValues()
                            .putPlaylistId(playlistId)
                            .putTrackId(trackId)
                            .insert(context.getContentResolver());
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
    }
}
