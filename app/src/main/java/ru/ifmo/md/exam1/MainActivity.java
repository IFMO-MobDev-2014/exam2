package ru.ifmo.md.exam1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import ru.ifmo.md.exam1.data.MyProvider;
import ru.ifmo.md.exam1.data.Song;
import ru.ifmo.md.exam1.fragment.PlaylistListFragment;


public class MainActivity extends ActionBarActivity {

    private NavigationHelper navigationHelper;

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getSharedPreferences("MUSIC", 0).getBoolean("info_loaded", false)) {
            final ProgressDialog pd = new ProgressDialog(this);
            final ContentResolver cr = getContentResolver();
            pd.setMessage(getString(R.string.wait));
            pd.setCancelable(false);
            pd.show();

            AsyncTask<Void, Void, Void> at = new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    ContentValues playlist = new ContentValues();
                    playlist.put(MyProvider.PLAYLISTS_NAME, "All songs");
                    Uri uri = cr.insert(MyProvider.PLAYLISTS_CONTENT, playlist);
                    int plId = Integer.parseInt(uri.getLastPathSegment());

                    InputStream is = getResources().openRawResource(R.raw.music);
                    List<Song> songs = new Gson().fromJson(new BufferedReader(new InputStreamReader(is)), new TypeToken<List<Song>>(){}.getType());
                    int cnt = 0;
                    for (Song song : songs) {
                        // insert song to database
                        uri = cr.insert(MyProvider.SONGS_CONTENT, song.getCV());
                        int songId = Integer.parseInt(uri.getLastPathSegment());
                        // insert song to playlist
                        ContentValues cv = new ContentValues();
                        cv.put(MyProvider.PLAYLIST_PL_ID, plId);
                        cv.put(MyProvider.PLAYLIST_SONG_ID, songId);
                        cr.insert(MyProvider.PLAYLIST_CONTENT, cv);

                        cnt++;
                       // if (cnt == 100) break;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    getSharedPreferences("MUSIC", 0).edit().putBoolean("info_loaded", true).commit();

                    pd.dismiss();
                    Log.d(getClass().getName(), "Parsing completed");
                }
            };

            at.execute();
        }

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            navigationHelper = new NavigationHelper(getSupportFragmentManager());
            navigationHelper.openFragment(PlaylistListFragment.class, null, true);
        }
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
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
            fm.beginTransaction().commit();
        } else {
            finish();
        }
    }
}
