package ru.eugene.exam2.items;

/**
 * Created by eugene on 1/21/15.
 */
public class Item1Source {
    public static final String TABLE_NAME = "item1";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + COLUMN_NAME + " TEXT NOT NULL" +
            ", " + COLUMN_DATE + " INTEGER NOT NULL UNIQUE);";
}
