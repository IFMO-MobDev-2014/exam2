package ru.ifmo.md.exam1.provider.item;

import java.util.Date;

import android.database.Cursor;

import ru.ifmo.md.exam1.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code item} table.
 */
public class ItemCursor extends AbstractCursor {
    public ItemCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(ItemColumns.NAME);
        return getString(index);
    }
}
