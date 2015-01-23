package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentUris;
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

import java.util.ArrayList;


public class PlaylistActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private ListView listView;
    private MyAdapter adapter;
    int plId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        plId = getIntent().getIntExtra("plId",0);

        listView = (ListView) findViewById(R.id.play_list);
        adapter = new MyAdapter(new ArrayList<Track>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listListener);
        getLoaderManager().initLoader(1,null,this);

    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PlaylistActivity.this,InfoActivity.class);
            int dbid = adapter.data.get(position).id;
            intent.putExtra("dbId",dbid);
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        return new CursorLoader(this, MyProvider.PHOTOS_CONTENT_URI,null,DBHelper.KEY_RAW_TYPE + " = " + 1,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.data.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            String playlist = cursor.getString(7);

            if(playlist.contains(Integer.toString(plId))) {
                Track track = new Track();
                track.name = name;
                track.id = id;
                adapter.data.add(track);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
