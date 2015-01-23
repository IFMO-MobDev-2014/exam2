package ru.ifmo.md.exam1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Евгения on 30.11.2014.
 */
public class TracksContentProvider extends ContentProvider {

    // db
    private DBAdapter db;

    // used for the UriMacher
    private static final int PLAYLISTS = 10;
    private static final int TRACKS_BY_PLAYLIST = 20;
    private static final int ALL_TRACKS= 30;

    public static final String AUTHORITY = "ru.ifmo.md.exam1.TracksContentProvider";

    private static final String BASE_PLAYLISTS = "playlists";
    private static final String BASE_TRACKS = "tracks";

    public static final Uri PLAYLISTS_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PLAYLISTS);
    public static final Uri TRACKS_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_TRACKS);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PLAYLISTS, PLAYLISTS);
        sURIMatcher.addURI(AUTHORITY, BASE_TRACKS + "/#", TRACKS_BY_PLAYLIST);
        sURIMatcher.addURI(AUTHORITY, BASE_TRACKS, ALL_TRACKS);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        if (uriType == TRACKS_BY_PLAYLIST) {
            int result = db.deleteTracks(selection);
            getContext().getContentResolver().notifyChange(uri, null);
            return result;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = -1;
        if (uriType == TRACKS_BY_PLAYLIST) {
            id = db.addTrack(values);
        } else if (uriType == PLAYLISTS) {
            id = db.addPlaylist(values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri.toString() + "/" + id);
    }

    @Override
    public boolean onCreate() {
        db = DBAdapter.getOpenedInstance(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);
        Cursor result = null;
        if (uriType == ALL_TRACKS) {
            result = db.getTracks(selection);
        }
        else if (uriType == TRACKS_BY_PLAYLIST) {
            String lastPathSegment = uri.getLastPathSegment();
            long playlistId = Long.parseLong(lastPathSegment);
            result = db.getTracksByPlaylistId(playlistId);
        } else if (uriType == PLAYLISTS) {
            result = db.getPlaylists(selection);
        }
        if (result != null)
            result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Updates are not supported");
    }
}

