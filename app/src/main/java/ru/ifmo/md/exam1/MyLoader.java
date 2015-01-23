package ru.ifmo.md.exam1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class MyLoader extends AsyncTaskLoader <List <Song>> {

    ArrayList <Song> songs;
    Context context;

    MyLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public List <Song> loadInBackground() {
        songs = new ArrayList<Song>();
        Cursor cursor = context.getContentResolver().query(MyContentProvider.SONGS_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String artist = cursor.getString(cursor.getColumnIndex(SQLiteHelper.ARTIST));
                String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAME));
                String genre = cursor.getString(cursor.getColumnIndex(SQLiteHelper.GENRES));
                int popularity = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.POPULARITY));
                int year = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.YEAR));
                String[] genres = genre.split(",");
                songs.add(new Song(artist, name, genres, popularity, year));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return songs;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}