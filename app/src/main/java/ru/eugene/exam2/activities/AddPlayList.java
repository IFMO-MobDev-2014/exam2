package ru.eugene.exam2.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import ru.eugene.exam2.R;
import ru.eugene.exam2.db.SongsProvider;
import ru.eugene.exam2.items.PlayListsSource;
import ru.eugene.exam2.items.SongsSource;

public class AddPlayList extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayAdapter<String> adapter;
    private Spinner years;
    private HashSet<Integer> set = new HashSet<>();
    private HashSet<String> setGen = new HashSet<>();
    private ListView listGen;
    private ArrayList<String> arrayGen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_play_list);

        years = (Spinner) findViewById(R.id.spinner);
        listGen = (ListView) findViewById(R.id.list_gen);

        adapter = new ArrayAdapter<String>(this, R.layout.item_check, arrayGen) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.item_check, null);

                TextView view = (TextView) v.findViewById(R.id.curGen);
                view.setText(arrayGen.get(position));

                return v;
            }
        };
        listGen.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_play_list, menu);
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
        return new CursorLoader(this, SongsProvider.CONTENT_URI_SONG, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;

        if (data.moveToFirst()) {
            do {
                int year = data.getInt(data.getColumnIndex(SongsSource.COLUMN_YEAR));
                String gen = data.getString(data.getColumnIndex(SongsSource.COLUMN_GENRES));
                set.add(year);
                setGen.add(gen);
            } while (data.moveToNext());
        }

        Integer[] array = new Integer[set.size()];
        array = set.toArray(array);
        Arrays.sort(array);

        Log.e("LOG", "size: " + setGen.size());
        String[] arrayGen = new String[setGen.size()];
        arrayGen = setGen.toArray(arrayGen);
        Arrays.sort(arrayGen);

        for (String i : arrayGen) {
            this.arrayGen.add(i);
        }
        Log.e("LOG", "size: " + this.arrayGen.size());
        adapter.notifyDataSetChanged();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        adapter.swapCursor(null);
    }
}
