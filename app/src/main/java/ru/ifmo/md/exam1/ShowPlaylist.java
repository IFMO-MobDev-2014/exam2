package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class ShowPlaylist extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_playlist);
        listView = (ListView) findViewById(R.id.listView2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_playlist, menu);
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
        String[] projection = {DBHelper.SONGS_COLUMN_NAME, DBHelper.SONGS_COLUMN_ARTIST, DBHelper.SONGS_COLUMN_YEAR};
        return new CursorLoader(this, DBContentProvider.SONGS, projection, null, null, null);
    }

    public void setListView(Cursor cursor) {
        cursor.moveToFirst();
        ArrayList<Song> items = new ArrayList<Song>();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.SONGS_COLUMN_NAME));
            String artist = cursor.getString(cursor.getColumnIndex(DBHelper.SONGS_COLUMN_ARTIST));
            int year = cursor.getInt(cursor.getColumnIndex(DBHelper.SONGS_COLUMN_YEAR));
            cursor.moveToNext();
            items.add(new Song(name, artist, year));
        }
        cursor.close();
        MyAdapter2 adapter = new MyAdapter2(items, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setListView(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
