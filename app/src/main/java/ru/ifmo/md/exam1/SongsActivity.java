package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class SongsActivity extends ActionBarActivity {

    public static final String PLAYLIST_ID = "playlist_id";
    private int playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        playlistId = getIntent().getIntExtra(PLAYLIST_ID, -1);
        if (playlistId == -1) {
            Toast.makeText(getApplicationContext(), R.string.unknown_error, Toast.LENGTH_SHORT).show();
        }
        getLoaderManager().initLoader(0, null, new MyLoaderManager());
        ListView listView = (ListView) findViewById(R.id.song_view);
        listView.setAdapter(new SongsAdapter(this, null, true));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_songs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class MyLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new CursorLoader(
                    getApplicationContext(),
                    DatabaseContentProvider.URI_FEED_DIR.buildUpon().appendPath("" + playlistId).build(),
                    new String[]{SongsTable.SONG_NAME},//...
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> objectLoader, Cursor cursor) {
            ((CursorAdapter) ((ListView) findViewById(R.id.song_view)).getAdapter()).swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> objectLoader) {
            ((CursorAdapter) ((ListView) findViewById(R.id.song_view)).getAdapter()).swapCursor(null);
        }
    }
}
