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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import ru.eugene.exam2.R;
import ru.eugene.exam2.db.SongsProvider;
import ru.eugene.exam2.items.PlayList;
import ru.eugene.exam2.items.PlayListsSource;
import ru.eugene.exam2.items.SongsSource;

public class AddPlayList extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayAdapter<String> adapter;
    private Spinner years;
    private HashSet<Integer> set = new HashSet<>();
    private HashSet<String> setGen = new HashSet<>();
    private ListView listGen;
    private ArrayList<String> arrayGen = new ArrayList<>();
    private TextView name;
    private String resGen = "";
    private ArrayList<Boolean> used = new ArrayList<>();

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

        listGen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("LOG", "click");
                used.set(position, !used.get(position));
                ((CheckBox) view.findViewById(R.id.checkGen)).setChecked(used.get(position));
            }
        });

        name = (TextView) findViewById(R.id.newName);

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
                if (!gen.isEmpty())
                    setGen.add(gen);
            } while (data.moveToNext());
        }

        Integer[] array = new Integer[set.size()];
        array = set.toArray(array);
        Arrays.sort(array);

        String[] arrayGen = new String[setGen.size()];
        arrayGen = setGen.toArray(arrayGen);
        Arrays.sort(arrayGen);

        for (String i : arrayGen) {
            this.arrayGen.add(i);
            used.add(false);
        }
        adapter.notifyDataSetChanged();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years.setAdapter(adapter);
    }

    public void finish(View v) {

        PlayList playList = new PlayList();
        playList.setName("all songs");
        playList.setDate(getCurDate());
        playList.setName(name.getText().toString());
        playList.setYear(Integer.parseInt(years.getSelectedItem().toString()));

        int i = 0;
        for (Boolean b : used) {
            if (b) {
                if (resGen.isEmpty()) {
                    resGen = SongsSource.COLUMN_GENRES + "='" + arrayGen.get(i) + "'";
                } else {
                    resGen += " AND " + SongsSource.COLUMN_GENRES + "='" + arrayGen.get(i) + "'";
                }
            }
           i++;
        }
        Log.e("LOG", "newGen" + resGen);
        playList.setGenres(resGen);

        Log.e("LOG", "before insert");
        getContentResolver().insert(SongsProvider.CONTENT_URI_PLAY_LIST, playList.generateContentValues());
        finish();
    }

    private String getCurDate() {
        Long curMillis = System.currentTimeMillis();
        Date curDate = new Date(curMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        formatter.setTimeZone(cal.getTimeZone());

        return formatter.format(curDate);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        adapter.swapCursor(null);
    }
}
