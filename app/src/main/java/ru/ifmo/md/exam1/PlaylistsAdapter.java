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
public class PlaylistsAdapter extends CursorAdapter {

    public PlaylistsAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    public void applySettings(View view, final Context context, final Cursor cursor) {
        TextView name = ((TextView) view.findViewById(R.id.playlistName));
        TextView authors = ((TextView) view.findViewById(R.id.authors));
        TextView years = ((TextView) view.findViewById(R.id.years));
        TextView janres = ((TextView) view.findViewById(R.id.janres));
        name.setText(cursor.getString(cursor.getColumnIndex(PlaylistsTable.PLAYLIST_NAME)));
        authors.setText(cursor.getString(cursor.getColumnIndex(PlaylistsTable.PLAYLIST_NAME)));
        years.setText(cursor.getString(cursor.getColumnIndex(PlaylistsTable.PLAYLIST_NAME)));
        janres.setText(cursor.getString(cursor.getColumnIndex(PlaylistsTable.PLAYLIST_NAME)));
        final int id = cursor.getInt(cursor.getColumnIndex(PlaylistsTable.COLUMN_ROWID_AFTER_QUERY));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SongsActivity.class);
                intent.putExtra(SongsActivity.PLAYLIST_ID, id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public View newView(final Context context, final Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.playlist_layout, null);
        applySettings(view, context, cursor);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        applySettings(view, context, cursor);
    }
}

