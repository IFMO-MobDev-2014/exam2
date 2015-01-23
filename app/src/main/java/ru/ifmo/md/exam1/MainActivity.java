package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import ru.ifmo.md.exam1.db.MyContentProvider;
import ru.ifmo.md.exam1.db.SongsBase;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    String[] projection = new String[]{
            SongsBase._ID,
            SongsBase.ARTIST,
            SongsBase.NAME,
            SongsBase.DURATION,
            SongsBase.POPULARITY,
            SongsBase.YEAR
    };

    SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream inputStream = getResources().openRawResource(R.raw.music);

        Cursor cursor = getContentResolver()
                .query(MyContentProvider.QUERY, projection, null, null, null);

        if (cursor.getCount() == 0) {
            ArrayList<Parser.Item> items = Parser.parse(inputStream);
            for (int i = 0; i < items.size(); i++) {
                ContentValues value = new ContentValues();
                value.put(SongsBase.NAME, items.get(i).name);
                value.put(SongsBase.ARTIST, items.get(i).artist);
                value.put(SongsBase.DURATION, items.get(i).duration);
                value.put(SongsBase.YEAR, items.get(i).year);
                value.put(SongsBase.POPULARITY, items.get(i).popularity);

                getContentResolver().insert(MyContentProvider.QUERY, value);
            }
        }

        String[] from = new String[]{SongsBase.ARTIST, SongsBase.NAME};
        int[] to = new int[]{R.id.textView, R.id.textView2};
        dataAdapter = new SimpleCursorAdapter(this, R.layout.song,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView listView = (ListView) findViewById(R.id.listView);
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
        return new CursorLoader(this, MyContentProvider.QUERY, projection, null, null, null);
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
        if (id == R.id.action_songs) {
            return true;
        } else if (id == R.id.action_playlists) {
            Intent intent = new Intent(this, PlaylistsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
