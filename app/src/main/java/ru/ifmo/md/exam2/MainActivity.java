package ru.ifmo.md.exam2;

import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import ru.ifmo.md.exam2.provider.playlist.PlaylistColumns;
import ru.ifmo.md.exam2.provider.playlist.PlaylistContentValues;
import ru.ifmo.md.exam2.provider.playlist.PlaylistCursor;
import ru.ifmo.md.exam2.provider.playlist.PlaylistSelection;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackContentValues;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackCursor;
import ru.ifmo.md.exam2.provider.playlisttotrack.PlaylistToTrackSelection;
import ru.ifmo.md.exam2.provider.track.TrackCursor;
import ru.ifmo.md.exam2.provider.track.TrackSelection;

public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ListView.OnItemClickListener {

    private PlaylistSelection playlistSelection = new PlaylistSelection();
    private SimpleCursorAdapter adapter;
    private int loadersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView playlistsList = (ListView) findViewById(R.id.playlists_list);
        String[] from = {
                PlaylistColumns.NAME
        };
        int[] to = {
                R.id.playlist_name
        };
        adapter = new SimpleCursorAdapter(this, R.layout.playlist_row,
                playlistSelection.query(getContentResolver()), from, to, 0);
        playlistsList.setAdapter(adapter);
        playlistsList.setOnItemClickListener(this);
        getLoaderManager().initLoader(loadersCount++, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_playlist) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_playlist_dialog);

            final EditText newPlaylistName = (EditText) dialog.findViewById(R.id.new_playlist_name);
            final EditText newPlaylistAuthor = (EditText) dialog.findViewById(R.id.new_playlist_author);
            Button newPlaylistOkButton = (Button) dialog.findViewById(R.id.new_playlist_add_button);
            final Spinner newPlaylistsYearSpinner =
                    (Spinner) dialog.findViewById(R.id.new_playlist_year_spinner);
            newPlaylistsYearSpinner.setAdapter(new YearAdapter(this));
            newPlaylistsYearSpinner.setSelection(2015);
            final Context context = this;
            newPlaylistOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = newPlaylistName.getText().toString();
                    final String author = newPlaylistAuthor.getText().toString();
                    Integer yearBuf = newPlaylistsYearSpinner.getSelectedItemPosition();
                    if (yearBuf == 2015) {
                        yearBuf = null;
                    } else if (yearBuf > 2015) {
                        yearBuf--;
                    }

                    final Integer year = yearBuf;

                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            TrackSelection trackSelection = new TrackSelection()
                                    .authorLike(author);

                            if (year == null) {
                                trackSelection.year(year);
                            }
                            TrackCursor cursor = trackSelection
                                    .query(getContentResolver());

                            PlaylistContentValues values = new PlaylistContentValues();
                            values.putName(name);
                            values.insert(getContentResolver());
                            PlaylistCursor playlistCursor = new PlaylistSelection()
                                    .name(name)
                                    .query(getContentResolver());
                            playlistCursor.moveToFirst();
                            long playlistId = playlistCursor.getId();

                            if (cursor.moveToFirst()) {
                                do {
                                    new PlaylistToTrackContentValues()
                                            .putPlaylistId(playlistId)
                                            .putTrackId(cursor.getId())
                                            .insert(getContentResolver());
                                } while (cursor.moveToNext());
                            }

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            progressDialog.dismiss();
                        }
                    }.execute();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, playlistSelection.uri(),
                null, playlistSelection.sel(), playlistSelection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        Intent intent = new Intent(this, PlaylistViewActivity.class);
        intent.putExtra(PlaylistViewActivity.PLAYLIST_ID_EXTRA_KEY, id);
        startActivity(intent);
    }
}
