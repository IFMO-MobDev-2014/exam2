package ru.ifmo.md.exam1.provider.playlists;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam1.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code playlists} table.
 */
public class PlaylistsCursor extends AbstractCursor {
    public PlaylistsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(PlaylistsColumns.NAME);
        return getString(index);
    }
}
