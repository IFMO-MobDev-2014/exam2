package ru.ifmo.md.exam1;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;


public class AddPlaylistActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);

        Spinner sp = (Spinner) findViewById(R.id.ddArtist);
        ArrayList<String> artists = new ArrayList<>();
        artists.add("");
        artists.addAll(DBAdapter.getOpenedInstance(this).getArtists());
        sp.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                artists));

        sp = (Spinner) findViewById(R.id.ddYear);

        ArrayList<String> years = new ArrayList<>();
        years.add("");
        years.addAll(DBAdapter.getOpenedInstance(this).getYears());
        sp.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                years));


        ArrayList<String> genres = new ArrayList<>();
        genres.add("");
        genres.addAll(DBAdapter.getOpenedInstance(this).getGenres());
        sp = (Spinner) findViewById(R.id.ddGenre);
        sp.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                genres));

        findViewById(R.id.btnAdd).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_playlist, menu);
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
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            String name = ((EditText) findViewById(R.id.edtName)).getText().toString();
            if (name != null && name.length() > 0) {
                DBAdapter db = DBAdapter.getOpenedInstance(this);
                long playlistId = db.addPlaylist(new Playlist(name));
                HashSet<Long> ids = new HashSet<>();
                Spinner sp = (Spinner) findViewById(R.id.ddArtist);
                String s = sp.getSelectedItem().toString();
                if (s.length() > 0) {
                    Cursor c = db.getTracksByArtist(s);
                    if (c.moveToFirst()) {
                        int columnId = c.getColumnIndex(DBAdapter.KEY_ID);
                        do {
                            long id = c.getLong(columnId);
                            if (!ids.contains(id))
                            {
                                ids.add(id);
                                db.addTrackToPlaylist(id, playlistId);
                            }
                        } while (c.moveToNext());
                    }
                }
                sp = (Spinner) findViewById(R.id.ddYear);
                s = sp.getSelectedItem().toString();
                if (s.length() > 0) {
                    Cursor c = db.getTracks(DBAdapter.KEY_TRACKS_YEAR+"='"+s+"'");
                    if (c.moveToFirst()) {
                        int columnId = c.getColumnIndex(DBAdapter.KEY_ID);
                        do {
                            long id = c.getLong(columnId);
                            if (!ids.contains(id))
                            {
                                ids.add(id);
                                db.addTrackToPlaylist(id, playlistId);
                            }
                        } while (c.moveToNext());
                    }
                }
                sp = (Spinner) findViewById(R.id.ddGenre);
                s = sp.getSelectedItem().toString();
                if (s.length() > 0) {
                    Cursor c = db.getTracksByGenre(db.findGenreId(s));
                    if (c.moveToFirst()) {
                        int columnId = c.getColumnIndex(DBAdapter.KEY_ID);
                        do {
                            long id = c.getLong(columnId);
                            if (!ids.contains(id))
                            {
                                ids.add(id);
                                db.addTrackToPlaylist(id, playlistId);
                            }
                        } while (c.moveToNext());
                    }
                }
                finish();
            }
        }
    }
}
