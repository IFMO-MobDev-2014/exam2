package ru.ifmo.md.exam2;

import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        AddDialogFragment.NoticeDialogListener{

    private SimpleCursorAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String from[] = { Tables.Playlists.TITLE_NAME, Tables.Playlists.AUTHOR_NAME};
        int to[] = { android.R.id.text1, android.R.id.text2 };
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null, from, to);

        listView = (ListView) findViewById(R.id.play_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listener);

        getLoaderManager().initLoader(0, null, this);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = ((Cursor) adapter.getItem(position));
            Intent intent = new Intent(MainActivity.this, TracksActivity.class);
            intent.putExtra(TracksActivity.EXTRA_LIST, cursor.getString(cursor.getColumnIndex(Tables.Playlists.TITLE_NAME)));
            intent.putExtra(TracksActivity.EXTRA_AUTHOR, cursor.getString(cursor.getColumnIndex(Tables.Playlists.AUTHOR_NAME)));
            intent.putExtra(TracksActivity.EXTRA_YEAR, cursor.getString(cursor.getColumnIndex(Tables.Playlists.YEAR_NAME)));
            intent.putExtra(TracksActivity.EXTRA_GENRES, cursor.getString(cursor.getColumnIndex(Tables.Playlists.GENRES_NAME)));
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            DialogFragment dialogFragment = new AddDialogFragment();
            dialogFragment.show(getFragmentManager(), "task");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final String[] SUMMARY_PROJECTION = new String[] {
            Tables.Playlists._ID,
            Tables.Playlists.TITLE_NAME,
            Tables.Playlists.AUTHOR_NAME,
            Tables.Playlists.YEAR_NAME,
            Tables.Playlists.GENRES_NAME
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Tables.Playlists.CONTENT_URI;

        return new CursorLoader(getBaseContext(), baseUri,
                SUMMARY_PROJECTION, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onDialogPositiveClick(String title, String author, String year) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Playlists.TITLE_NAME, title);
        cv.put(Tables.Playlists.AUTHOR_NAME, author);
        cv.put(Tables.Playlists.YEAR_NAME, year);
        getContentResolver().insert(Tables.Playlists.CONTENT_URI, cv);
    }
}
