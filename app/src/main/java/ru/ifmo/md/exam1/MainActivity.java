package ru.ifmo.md.exam1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private static final String MY_TAG = "EXAM2_MAIN";
    ListView lv;
    Adapter tracklistAdapter, playlistAdapter;
    private final ArrayList<Track> trackList = new ArrayList<Track>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMusic();
        Log.d(MY_TAG, "there is " + trackList.size() + " loaded tracks");
        if (!trackList.isEmpty()) {
            Log.d(MY_TAG, "First track is '" + trackList.get(0).toString() + "'");
        }
        lv = (ListView)findViewById(R.id.listView);
        tracklistAdapter = new TrackListAdapter(this, android.R.layout.simple_list_item_1, trackList);
        lv.setAdapter((TrackListAdapter)tracklistAdapter);
        // lv.deferNotifyDataSetChanged();
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(showFullInfo(trackList.get(position)));
                        builder.setItems(new String[] {"Delete track", "Add to playlist", "Download"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    deleteTrack(position);
                                }
                                switch (which) {
                                    case 0:
                                        deleteTrack(position);
                                        break;
                                    case 1:
                                        addToList(position);
                                        break;
                                    case 2:
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trackList.get(position).url));
                                        startActivity(browserIntent);
                                }
                            }
                        });
                        builder.create().show();
                    }

                    private String showFullInfo(Track track) {
                        return track.name + " (" + track.duration + ") - " + track.year;
                    }
                }
        );
    }

    private void addToList(int position) {

    }

    private void deleteTrack(int position) {
        String url = trackList.get(position).url;
        trackList.remove(position);
        ((TrackListAdapter)tracklistAdapter).notifyDataSetChanged();
    }

    // {"name":"Mapei – Don't Wait",
// "url":"https://cs1-46v4.vk-cdn.net/p23/53f922c0d2cec9.mp3",
// "duration":"3:35",
// "popularity":76,
// "genres":["Blues","Alternative","Soul"],
// "year":2009}
    private String[] getStringArray(JsonReader reader) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(reader.nextString());
        }
        reader.endArray();

        return result.toArray(new String[result.size()]);
    }
    private Track readTrack(JsonReader reader) throws IOException {
        Track result = new Track();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                result.name = reader.nextString();
            } else if (name.equals("url")) {
                result.url = reader.nextString();
            } else if (name.equals("duration")) {
                result.duration = reader.nextString();
            } else if (name.equals("popularity")) {
                result.popularity = reader.nextInt();
            } else if (name.equals("year")) {
                result.year = reader.nextInt();
            } else if (name.equals("genres")) {
                result.genres = getStringArray(reader);
            }
        }
        reader.endObject();

        return result;
    }
    private void loadMusic() {
        try {
            JsonReader reader = new JsonReader(
                new InputStreamReader(
                        getResources().openRawResource(R.raw.music)
                )
            );
            reader.beginArray();
            while (reader.hasNext()) {
                trackList.add(readTrack(reader));
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            Log.e(MY_TAG, e.getMessage());
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addPlaylist(MenuItem item) {
    }

    public void showPlaylists(MenuItem item) {
    }
}
