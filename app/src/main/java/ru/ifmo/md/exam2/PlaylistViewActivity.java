package ru.ifmo.md.exam2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.ifmo.md.exam2.R;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackCursor;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackSelection;

public class PlaylistViewActivity extends ActionBarActivity {

    public static final String PLAYLIST_ID_EXTRA_KEY = "playlist_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);

        final long playlistId = getIntent().getLongExtra(PLAYLIST_ID_EXTRA_KEY, -1);

        final ListView tracksList = (ListView) findViewById(R.id.tracks_list);

        final Context context = this;

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        dialog.setCancelable(false);
        new AsyncTask<Void, Void, Track[]>() {
            @Override
            protected Track[] doInBackground(Void... params) {
                Log.d("STARTED", "Started fetching list");
                PlaylistToTrackCursor cursor = new PlaylistToTrackSelection()
                        .playlistId(playlistId)
                        .query(getContentResolver());
                Track[] tracks = new Track[cursor.getCount()];
                int position = 0;
                if (cursor.moveToFirst()) {
                    do {
                        tracks[position++] = new Track(
                                cursor.getTrackTitle(),
                                cursor.getTrackAuthor(),
                                cursor.getTrackYear(),
                                new GenresMask(cursor.getTrackGenresMask()));
                    } while (cursor.moveToNext());
                }

                return tracks;
            }

            @Override
            protected void onPostExecute(Track[] tracks) {
                super.onPostExecute(tracks);
                tracksList.setAdapter(new TrackListAdapter(tracks, context));
                dialog.dismiss();
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playlist_view, menu);
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
