package ru.ifmo.md.exam2;

import android.app.LoaderManager;
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

import ru.ifmo.md.exam2.R;

public class TracksActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    private SimpleCursorAdapter adapter;
    private ListView listView;

    public static final String EXTRA_LIST = "extra_list";
    public static final String EXTRA_AUTHOR = "extra_author";
    public static final String EXTRA_YEAR = "extra_year";
    public static final String EXTRA_GENRES = "extra_genres";

    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        String from[] = { Tables.Tracks.TITLE_NAME, Tables.Tracks.AUTHOR_NAME };
        int to[] = { android.R.id.text1, android.R.id.text2 };
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null, from, to);

        listView = (ListView) findViewById(R.id.tracks_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listener);

        Intent intent = getIntent();
        if (intent.getStringExtra(EXTRA_LIST).equals("All tracks")) {
            selection = null;
        } else {
            String author = intent.getStringExtra(EXTRA_AUTHOR);
            String year = intent.getStringExtra(EXTRA_YEAR);
            if (author != null || year != null) {
                if (author != null && !author.isEmpty()) {
                    selection = "(" + Tables.Playlists.AUTHOR_NAME + "=" + "\"" + author + "\")";
                }
                if (year != null && !year.isEmpty()) {
                    if (author != null) {
                        selection += " AND ";
                    }
                    selection += "(" + Tables.Playlists.YEAR_NAME + "=" + "\"" + year + "\")";
                }
            } else {
                selection = null;
            }
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = ((Cursor) adapter.getItem(position));
            Intent intent = new Intent(TracksActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_AUTHOR    , cursor.getString(cursor.getColumnIndex(Tables.Tracks.AUTHOR_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_TITLE     , cursor.getString(cursor.getColumnIndex(Tables.Tracks.TITLE_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_URL       , cursor.getString(cursor.getColumnIndex(Tables.Tracks.URL_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_DURATION  , cursor.getString(cursor.getColumnIndex(Tables.Tracks.DURATION_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_POPULARITY, cursor.getString(cursor.getColumnIndex(Tables.Tracks.POPULARITY_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_GENRES    , cursor.getString(cursor.getColumnIndex(Tables.Tracks.GENRES_NAME)));
            intent.putExtra(DetailsActivity.EXTRA_YEAR      , cursor.getString(cursor.getColumnIndex(Tables.Tracks.YEAR_NAME)));
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
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

    static final String[] SUMMARY_PROJECTION = new String[] {
            Tables.Tracks._ID,
            Tables.Tracks.AUTHOR_NAME,
            Tables.Tracks.TITLE_NAME,
            Tables.Tracks.URL_NAME,
            Tables.Tracks.DURATION_NAME,
            Tables.Tracks.POPULARITY_NAME,
            Tables.Tracks.GENRES_NAME,
            Tables.Tracks.YEAR_NAME
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Tables.Tracks.CONTENT_URI;

        return new CursorLoader(getBaseContext(), baseUri,
                SUMMARY_PROJECTION, selection, null,
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
}
