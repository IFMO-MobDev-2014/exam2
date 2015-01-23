package ru.ya.exam2.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TreeSet;

import ru.ya.exam2.R;
import ru.ya.exam2.database.MContentProvider;
import ru.ya.exam2.database.MSQLiteHelper;

public class PlayListActivity extends ActionBarActivity {
    ArrayAdapter < String > adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        int position = Integer.valueOf(getIntent().getStringExtra(MSQLiteHelper.COLUMN_ID));
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, R.layout.play_list_aaaa) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View mView;
                if (convertView == null)
                    mView = LayoutInflater.from(PlayListActivity.this).inflate(R.layout.play_list_aaaa, parent, false);
                else mView = convertView;
                ((TextView)mView.findViewById(R.id.TextView)).setText(getItem(position));
                return mView;
            }
        };
        Log.e("position: ", "" + position);

        Cursor cursor = getContentResolver().query(MContentProvider.URI_B, null, MSQLiteHelper.COLUMN_ID + "=?",
              new String[] {String.valueOf(position)}, null);
        if (cursor == null || cursor.getCount() != 1) throw new Error();
        cursor.moveToFirst();
        String ids = cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_SONGS_ID));
        Log.e("ids: ", ids);
        TreeSet< String > songsId = new TreeSet<>();

        for (int i = 0; i < ids.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (; ids.charAt(i) != '|'; i++)
                sb.append(ids.charAt(i));
            Log.e("add item: ", sb.toString());
            songsId.add(sb.toString());
        }

        /// second part


        cursor = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
        int sz = cursor.getCount();
        for (int i = 0; i < sz; i++) {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_ID));
            if (songsId.contains(id))
                adapter.add(cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_TITLE)));
        }

        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play_list, menu);
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
}
