package ru.eugene.exam2.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.eugene.exam2.R;
import ru.eugene.exam2.db.SongsProvider;
import ru.eugene.exam2.items.SongsSource;

public class ViewPlayList extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView list;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_play_list);

        String[] from = new String[] {SongsSource.COLUMN_NAME, SongsSource.COLUMN_ARTIST};
        int[] to = new int[] {R.id.name_s, R.id.artist};
        list = (ListView) findViewById(R.id.play_list);

        adapter = new SimpleCursorAdapter(this, R.layout.play_list_item, null, from, to, 0);
        list.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_play_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, SongsProvider.CONTENT_URI_SONG, null, null, null, null);
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
