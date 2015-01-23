package ru.ifmo.md.exam1.provider.item;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code item} table.
 */
public class ItemContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ItemColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, ItemSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ItemContentValues putName(String value) {
        mContentValues.put(ItemColumns.NAME, value);
        return this;
    }

    public ItemContentValues putNameNull() {
        mContentValues.putNull(ItemColumns.NAME);
        return this;
    }

}
