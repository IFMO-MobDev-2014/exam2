package ru.eugene.exam2.items;

/**
 * Created by eugene on 1/21/15.
 */
public class PlayListsSource {
    public static final String TABLE_NAME = "playList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_GENRES = "genres";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + COLUMN_NAME + " TEXT" +
            ", " + COLUMN_ARTIST + " TEXT" +
            ", " + COLUMN_YEAR + " INTEGER" +
            ", " + COLUMN_DATE + " INTEGER" +
            ", " + COLUMN_GENRES + " TEXT);";
}
