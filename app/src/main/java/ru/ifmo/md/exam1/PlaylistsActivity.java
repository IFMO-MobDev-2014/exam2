package ru.ifmo.md.exam1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PlaylistsActivity extends ActionBarActivity {

    private PlaylistAdapter adapter;
    private ListView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists);
        adapter = new PlaylistAdapter(new ArrayList<String>(), this);
        view = (ListView) findViewById(R.id.listView2);
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("All songs");
        adapter.setStr(tmp);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlaylistsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
