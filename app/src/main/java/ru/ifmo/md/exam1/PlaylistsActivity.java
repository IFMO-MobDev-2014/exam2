package ru.ifmo.md.exam1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class PlaylistsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String INIT = "init";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        ((ListView) findViewById(R.id.playlist_view)).setAdapter(new PlaylistsAdapter(this, null, true));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.contains(INIT)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(INIT, true);
            editor.apply();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_playlist) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
