package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class SongsListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    SongsAdapter adapter;
    ListView listView;
    String name;
    String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        listView = (ListView) findViewById(R.id.songs_list);
        adapter = new SongsAdapter();
        listView.setAdapter(adapter);
        name = getIntent().getStringExtra("name");
        Cursor cursor = getContentResolver().query(MyContentProvider.PLAYLIST_CONTENT_URI, null, SongsDBHelper.PLAYLIST_NAME + "='" + name + "'", null, null);
        cursor.moveToNext();
        String[] s = cursor.getString(cursor.getColumnIndex(SongsDBHelper.PLAYLIST_SONGS)).split("\\|");
        q = "";
        for (int i = 0; i < s.length; i++) {
            if (i != 0) {
                q += " OR ";
            }
            q += SongsDBHelper.SONG_ID + "=" + s[i];
        }
        getLoaderManager().restartLoader(2222, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_songs_list, menu);
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
        if (name.equals("Все песни")) {
            return new CursorLoader(this, MyContentProvider.SONG_CONTENT_URI, null, null, null, null);
        } else {
            return new CursorLoader(this, MyContentProvider.SONG_CONTENT_URI, null, q, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            adapter.clear();
            while (data.moveToNext()) {
                Song s = SongsDBHelper.SongCursor.getSong(data);
                adapter.add(s);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
