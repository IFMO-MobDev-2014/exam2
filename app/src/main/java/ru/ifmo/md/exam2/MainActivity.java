package ru.ifmo.md.exam2;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistSelection;

public class MainActivity extends ActionBarActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private PlaylistSelection playlistSelection = new PlaylistSelection();
    private SimpleCursorAdapter adapter;
    private int loadersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView playlistsList = (ListView) findViewById(R.id.playlists_list);
        String[] from = {
                PlaylistColumns.NAME
        };
        int[] to = {
                R.id.playlist_name
        };
        adapter = new SimpleCursorAdapter(this, R.layout.playlist_row,
                    playlistSelection.query(getContentResolver()), from, to, 0);
        playlistsList.setAdapter(adapter);
        getLoaderManager().initLoader(loadersCount++, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, playlistSelection.uri(),
                null, playlistSelection.sel(), playlistSelection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
