package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.ifmo.md.exam1.database.Provider;
import ru.ifmo.md.exam1.database.genre.GenreContract;
import ru.ifmo.md.exam1.database.playlist.PlaylistContract;
import ru.ifmo.md.exam1.database.song.SongContract;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private SimpleCursorAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);

        String[] fromColumns = {PlaylistContract.Playlist.NAME};
        int[] toViews = {R.id.playlist_display_name};

        playlistAdapter = new SimpleCursorAdapter(this, R.id.list_item, null, fromColumns, toViews, 0);
        listView.setAdapter(playlistAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        return new CursorLoader(this, Provider.PLAYLIST_CONTENT_URI,
                PlaylistContract.Playlist.ALL_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            data = parse();
        }
        playlistAdapter.swapCursor(data);
    }

    private Cursor parse() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(getAssets().open("music.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonLine = "";
        try {
            String currentLine = in.readLine();
            while (currentLine != null) {
                jsonLine += currentLine;
                currentLine = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray allSongs;
        StringBuilder songs = new StringBuilder("[");
        try {
            allSongs = new JSONArray(jsonLine);
            for (int i = 0; i < allSongs.length(); ++i) {
                JSONObject song = allSongs.getJSONObject(i);
                songs.append(song.getString("name"));
                addSongToDatabase(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlaylistContract.Playlist.ID, "0");
        contentValues.put(PlaylistContract.Playlist.NAME, "All songs");
        contentValues.put(PlaylistContract.Playlist.SONGS, songs.toString());
        getContentResolver().insert(Provider.PLAYLIST_CONTENT_URI, contentValues);

        return getContentResolver().query(Provider.PLAYLIST_CONTENT_URI, PlaylistContract.Playlist.ALL_COLUMNS, null, null, null);
    }

    private int songID = 1;
    private int genreID = 1;
    private void addSongToDatabase(JSONObject song) throws JSONException {
        String songName = song.getString("name");
        String songUrl = song.getString("url");
        String songDurationString = song.getString("duration");
        long songPopularity = song.getLong("popularity");
        JSONArray genres = song.getJSONArray("genres");
        long songYear = song.getLong("year");

        ContentValues contentValues = new ContentValues();
        contentValues.put(SongContract.Song.ID, songID++);
        contentValues.put(SongContract.Song.NAME, songName);
        contentValues.put(SongContract.Song.URL, songUrl);
        contentValues.put(SongContract.Song.DURATION, songDurationString);
        contentValues.put(SongContract.Song.DURATION, songPopularity);
        contentValues.put(SongContract.Song.GENRES, genres.toString());
        contentValues.put(SongContract.Song.YEAR, songYear);
        contentValues.put(SongContract.Song.VALID_STATE, 1);
        getContentResolver().insert(Provider.SONG_CONTENT_URI, contentValues);

        for (int j = 0; j < genres.length(); ++j) {
            String genre = genres.getString(j);

            contentValues.clear();
            contentValues.put(GenreContract.Genre.ID, genreID++);
            contentValues.put(GenreContract.Genre.NAME, genre);
            contentValues.put(GenreContract.Genre.VALID_STATE, 1);
            getContentResolver().update(Provider.GENRE_CONTENT_URI, contentValues, null, null);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        playlistAdapter.swapCursor(null);
    }
}
