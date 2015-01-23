package ru.eugene.exam2.items;

/**
 * Created by eugene on 1/21/15.
 */
public class SongsSource {
    public static final String TABLE_NAME = "songs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_GENRES = "genres";
    public static final String COLUMN_ID2 = "id2";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", " + COLUMN_NAME + " TEXT NOT NULL" +
            ", " + COLUMN_YEAR + " INTEGER NOT NULL" +
            ", " + COLUMN_ARTIST + " TEXT NOT NULL" +
            ", " + COLUMN_URL + " TEXT" +
            ", " + COLUMN_DURATION + " STRING NOT NULL" +
            ", " + COLUMN_POPULARITY + " INTEGER NOT NULL" +
            ", " + COLUMN_GENRES + " STRING NOT NULL" +
            ", " + COLUMN_ID2 + " INTEGER NOT NULL);";
}
