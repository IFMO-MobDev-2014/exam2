package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.InputStream;
import java.util.ArrayList;

import ru.ifmo.md.exam1.db.MyContentProvider;
import ru.ifmo.md.exam1.db.PlayBase;
import ru.ifmo.md.exam1.db.SongsBase;

/**
 * Created by Яна on 23.01.2015.
 */
public class PlaylistsActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    String[] projection = new String[]{
            PlayBase._ID,
            PlayBase.NAME,
            PlayBase.SONGS
    };

    SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String[] from = new String[]{PlayBase.NAME};
        int[] to = new int[]{R.id.textView3};
        dataAdapter = new SimpleCursorAdapter(this, R.layout.play,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(dataAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, MyContentProvider.QUERY_PLAY, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        dataAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        dataAdapter.swapCursor(null);
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_songs_play) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_playlists_play) {
            return true;
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
