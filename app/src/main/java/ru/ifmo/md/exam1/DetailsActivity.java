package ru.ifmo.md.exam1;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class DetailsActivity extends ActionBarActivity implements View.OnClickListener {

    long trackId = -1;

    public static final String EXTRA_ID = "trackId";

    Spinner spPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackId = getIntent().getLongExtra(EXTRA_ID, -1);
        setContentView(R.layout.activity_details);
        Cursor c = DBAdapter.getOpenedInstance(this).getTracks(DBAdapter.KEY_ID+"='"+ trackId +"'");
        if (c.moveToFirst()) {
            ((TextView) findViewById(R.id.tvArtist)).setText(c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_ARTIST)));
            ((TextView) findViewById(R.id.tvTitle)).setText(c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_NAME)));
            ((TextView) findViewById(R.id.tvYear)).setText(c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_YEAR)));
            ((TextView) findViewById(R.id.tvPopularity)).setText(c.getString(c.getColumnIndex(DBAdapter.KEY_TRACKS_POPULARITY)));
        }
        findViewById(R.id.btnAdd).setOnClickListener(this);
        spPlaylist = (Spinner) findViewById(R.id.spPlaylists);
        spPlaylist.setAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line, DBAdapter.getOpenedInstance(this).getPlaylists(null),
                new String[]{DBAdapter.KEY_PLAYLISTS_NAME}, new int[]{android.R.id.text1}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            DBAdapter.getOpenedInstance(this).deleteTrackById(trackId);
            setResult(RESULT_OK);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            DBAdapter.getOpenedInstance(this).addTrackToPlaylist(trackId, spPlaylist.getSelectedItemId());
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
