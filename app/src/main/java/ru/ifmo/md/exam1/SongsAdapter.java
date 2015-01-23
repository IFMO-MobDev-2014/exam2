package ru.ifmo.md.exam1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by timur on 23.01.15.
 */
public class SongsAdapter extends CursorAdapter {

    public SongsAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    private void applySettings(View view, final Context context, final Cursor cursor) {
        TextView name = ((TextView) view.findViewById(R.id.songName));
        TextView authors = ((TextView) view.findViewById(R.id.author));
        TextView years = ((TextView) view.findViewById(R.id.year));
        TextView janres = ((TextView) view.findViewById(R.id.janres));
        TextView popularity = ((TextView) view.findViewById(R.id.popularity));
        name.setText(cursor.getString(cursor.getColumnIndex(SongsTable.SONG_NAME)));
        authors.setText(cursor.getString(cursor.getColumnIndex(SongsTable.SONG_NAME)));
        years.setText(cursor.getString(cursor.getColumnIndex(SongsTable.SONG_AUTHOR)));
        janres.setText(cursor.getString(cursor.getColumnIndex(SongsTable.SONG_JANRES)));
        popularity.setText(cursor.getString(cursor.getColumnIndex(SongsTable.SONG_POPULARITY)));
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_layout, null);
        applySettings(view, context, cursor);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        applySettings(view, context, cursor);
    }

}
