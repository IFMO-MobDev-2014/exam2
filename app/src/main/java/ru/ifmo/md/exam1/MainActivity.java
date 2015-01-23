package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener,
        AdapterView.OnItemClickListener {


    private ListView listView;
    private Button button;
    private PlayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        button = (Button) findViewById(R.id.createPlayList);
        adapter = new PlayListAdapter();
        listView.setAdapter(adapter);
        Cursor check = getContentResolver().query(MyContentProvider.PLAYLIST_CONTENT_URI, null, SongsDBHelper.PLAYLIST_NAME + "='Все песни'", null, null);
        check.moveToNext();
        if (check.isAfterLast()) {
            Cursor cursor = getContentResolver().query(MyContentProvider.SONG_CONTENT_URI, null, null, null, null);
            List<Integer> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(SongsDBHelper.SONG_ID));
                list.add(id);
            }
            PlayList playList = new PlayList("Все песни", list);
            getContentResolver().insert(MyContentProvider.PLAYLIST_CONTENT_URI, playList.getCV());
        }
        getLoaderManager().restartLoader(2222, null, this);
        listView.setOnItemClickListener(this);
        button.setOnClickListener(this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MyContentProvider.PLAYLIST_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            while (data.moveToNext()) {
                PlayList pl = SongsDBHelper.PlayListCursor.getPlayList(data);
                adapter.add(pl);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SongsListActivity.class);
        intent.putExtra("name", adapter.getItem(position).getName());
        startActivity(intent);
    }
}
