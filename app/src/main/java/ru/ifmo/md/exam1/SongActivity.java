package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SongActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ((TextView)findViewById(R.id.editArtist2)).setText(getIntent().getStringExtra("artist"));
        ((TextView)findViewById(R.id.editSong2)).setText(getIntent().getStringExtra("song"));
        ((TextView)findViewById(R.id.editDuration2)).setText(getIntent().getStringExtra("duration"));
        ((TextView)findViewById(R.id.editYear2)).setText(getIntent().getStringExtra("year"));
        String mask = getIntent().getStringExtra("genres_mask");
        String s = "";
        for (int i = 0; i < Constants.genres.length; i++) {
            if (mask.charAt(i) == '1') {
                if (!s.equals("")) {
                    s += ", ";
                }
                s += Constants.genres[i];
            }
        }
        ((TextView)findViewById(R.id.editGenre2)).setText(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song, menu);
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
}
