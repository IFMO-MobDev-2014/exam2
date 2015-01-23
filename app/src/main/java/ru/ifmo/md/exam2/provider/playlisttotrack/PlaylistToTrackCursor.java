/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlisttotrack;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam2.provider.base.AbstractCursor;
import ru.ifmo.md.exam2.provider.playlist.*;
import ru.ifmo.md.exam2.provider.track.*;

/**
 * Cursor wrapper for the {@code playlist_to_track} table.
 */
public class PlaylistToTrackCursor extends AbstractCursor {
    public PlaylistToTrackCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code playlist_id} value.
     */
    public long getPlaylistId() {
        return getLongOrNull(PlaylistToTrackColumns.PLAYLIST_ID);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getPlaylistName() {
        Integer index = getCachedColumnIndexOrThrow(PlaylistColumns.NAME);
        return getString(index);
    }

    /**
     * Get the {@code track_id} value.
     */
    public long getTrackId() {
        return getLongOrNull(PlaylistToTrackColumns.TRACK_ID);
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    public String getTrackTitle() {
        Integer index = getCachedColumnIndexOrThrow(TrackColumns.TITLE);
        return getString(index);
    }

    /**
     * Get the {@code author} value.
     * Cannot be {@code null}.
     */
    public String getTrackAuthor() {
        Integer index = getCachedColumnIndexOrThrow(TrackColumns.AUTHOR);
        return getString(index);
    }

    /**
     * Get the {@code year} value.
     */
    public int getTrackYear() {
        return getIntegerOrNull(TrackColumns.YEAR);
    }

    /**
     * Get the {@code genres_mask} value.
     */
    public long getTrackGenresMask() {
        return getLongOrNull(TrackColumns.GENRES_MASK);
    }
}
