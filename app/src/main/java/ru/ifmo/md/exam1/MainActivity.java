package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

import ru.ifmo.md.exam1.provider.song.SongColumns;
import ru.ifmo.md.exam1.provider.song.SongCursor;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ArrayAdapter<String> adapter;
    Random rng = new Random(58);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        getLoaderManager().restartLoader(0, null, this);
        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);
    }


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, SongColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        adapter.clear();
        while (cursor.moveToNext()) {
            SongCursor itemCursor = new SongCursor(cursor);
            adapter.add(itemCursor.getSong());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter = null;
    }

    public void onButton2Click(View view) {
        getContentResolver().delete(SongColumns.CONTENT_URI, null, null);
    }
}
