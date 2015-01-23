package ru.ifmo.md.exam1.provider.item;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ru.ifmo.md.exam1.provider.base.AbstractSelection;

/**
 * Selection for the {@code item} table.
 */
public class ItemSelection extends AbstractSelection<ItemSelection> {
    @Override
    public Uri uri() {
        return ItemColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ItemCursor} object, which is positioned before the first entry, or null.
     */
    public ItemCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public ItemCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public ItemCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ItemSelection id(long... value) {
        addEquals("item." + ItemColumns._ID, toObjectArray(value));
        return this;
    }


    public ItemSelection name(String... value) {
        addEquals(ItemColumns.NAME, value);
        return this;
    }

    public ItemSelection nameNot(String... value) {
        addNotEquals(ItemColumns.NAME, value);
        return this;
    }

    public ItemSelection nameLike(String... value) {
        addLike(ItemColumns.NAME, value);
        return this;
    }
}
