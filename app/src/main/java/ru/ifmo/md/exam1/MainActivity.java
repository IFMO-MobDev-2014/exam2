package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView listView;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_list);
        adapter = new MyAdapter(new ArrayList<Track>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listListener);
        getLoaderManager().initLoader(1,null,this);



        Intent servIntent = new Intent(this,MyIntentService.class);
        startService(servIntent);



        String playlists = "1 2 3 4 5";

        ArrayList<Integer> pl = new ArrayList<>();
        int space = playlists.indexOf(' ');
        pl.add(Integer.parseInt(playlists.substring(0,space)));

    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this,PlaylistActivity.class);
            intent.putExtra("dbId",position);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MyProvider.PHOTOS_CONTENT_URI,null,DBHelper.KEY_RAW_TYPE + " = " + 2,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.data.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            Track track = new Track();
            track.name = name;
            adapter.data.add(track);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter = new MyAdapter(new ArrayList<Track>());
        listView.setAdapter(adapter);
    }

    public void addListBtn(View view) {
        Intent intent2 = new Intent(this,NewListActivity.class);
        startActivity(intent2);
    }
}
