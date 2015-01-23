package ru.ifmo.md.exam1.provider.item;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.ifmo.md.exam1.provider.MyProvider;
import ru.ifmo.md.exam1.provider.item.ItemColumns;

/**
 * Columns for the {@code item} table.
 */
public class ItemColumns implements BaseColumns {
    public static final String TABLE_NAME = "item";
    public static final Uri CONTENT_URI = Uri.parse(MyProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String NAME = "name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == NAME || c.contains("." + NAME)) return true;
        }
        return false;
    }

}
