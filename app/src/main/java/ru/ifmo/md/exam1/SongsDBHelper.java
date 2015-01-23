package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Алексей on 23.01.2015.
 */
public class SongsDBHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_NAME = "songs.db";
    public static final int DB_VERSION = 4;


    public static final String SONG_TABLE_NAME = "songs";
    public static final String SONG_ARTIST = "artist";
    public static final String SONG_NAME = "name";
    public static final String SONG_DURATION = "duration";
    public static final String SONG_YEAR = "year";
    public static final String SONG_POPULARITY = "popularity";
    public static final String SONG_GENRE = "genre";
    public static final String SONG_ID = _ID;
    public static final String CREATE_CITY_TABLE = "create table "
            + SONG_TABLE_NAME + " ("
            + SONG_ID + " integer primary key autoincrement, "
            + SONG_NAME + " text, "
            + SONG_ARTIST + " text, "
            + SONG_DURATION + " integer, "
            + SONG_YEAR + " integer, "
            + SONG_POPULARITY + " integer, "
            + SONG_GENRE + " text) ";

    public static final String PLAYLIST_TABLE_NAME = "playlists";
    public static final String PLAYLIST_NAME = "name";
    public static final String PLAYLIST_SONGS = "songs";
    public static final String PLAYLIST_ID = _ID;
    public static final String CREATE_WEATHER_TABLE = "create table "
            + PLAYLIST_TABLE_NAME + " ("
            + PLAYLIST_ID + " integer primary key autoincrement, "
            + PLAYLIST_NAME + " text, "
            + PLAYLIST_SONGS + " text)";
    Context context;
    public SongsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        Log.d("base", "start");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CITY_TABLE);
        sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);

        try {
            Scanner in = new Scanner(context.getAssets().open("music.txt"));
            String s = in.nextLine();
            Log.d("START", s);
            List<Song> songs = SongsParser.parse(s);
            for (Song song : songs) {
                sqLiteDatabase.insert(SONG_TABLE_NAME, null, song.getCV());
                Log.d("SONGS", song.toString());
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("drop table " + PLAYLIST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table " + SONG_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public static class SongCursor extends CursorWrapper {
        private Cursor cursor;

        public SongCursor(Cursor cursor) {
            super(cursor);
            this.cursor = cursor;
        }

        public static Song getSong(Cursor cursor) {
            Log.d("name", "/" + cursor.getColumnName(0).equals(SONG_ID) + "/");
            int id = cursor.getInt(cursor.getColumnIndex(SONG_ID));
            String name = cursor.getString(cursor.getColumnIndex(SONG_NAME));
            String artist = cursor.getString(cursor.getColumnIndex(SONG_ARTIST));
            int duration = cursor.getInt(cursor.getColumnIndex(SONG_DURATION));
            int year = cursor.getInt(cursor.getColumnIndex(SONG_YEAR));
            int popularity = cursor.getInt(cursor.getColumnIndex(SONG_POPULARITY));
            String genre = cursor.getString(cursor.getColumnIndex(SONG_GENRE));

            Song song = new Song(artist, name, duration, popularity, genre, year);
            song.setId(id);
            return song;
        }

        public Song getSong() {
            return getSong(cursor);
        }
    }

    public static class PlayListCursor extends CursorWrapper {
        private Cursor cursor;

        public PlayListCursor(Cursor cursor) {
            super(cursor);
            this.cursor = cursor;
        }

        public static PlayList getPlayList(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndex(PLAYLIST_ID));
            String name = cursor.getString(cursor.getColumnIndex(PLAYLIST_NAME));
            String songs = cursor.getString(cursor.getColumnIndex(PLAYLIST_SONGS));
            String[] so = songs.split("\\|");
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (String s : so) {
                arrayList.add(Integer.parseInt(s));

            }
            PlayList playList = new PlayList(name, arrayList);
            playList.setId(id);
            return playList;
        }

        public PlayList getPlayList() {
            return getPlayList(cursor);
        }
    }
}
