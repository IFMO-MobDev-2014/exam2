/*
 * This source file is generated with https://github.com/BoD/android-contentprovider-generator
 */
package ru.ifmo.md.exam2.provider.playlist;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam2.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code playlist} table.
 */
public class PlaylistCursor extends AbstractCursor {
    public PlaylistCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(PlaylistColumns.NAME);
        return getString(index);
    }
}
