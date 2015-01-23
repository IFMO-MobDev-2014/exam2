package ru.ifmo.md.exam1;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    public static final String LOG_TAG = "ru.ifmo.md.exam1.ActionBarActivity";

    private void firstRun() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("firstRunComplete"))
            return;

        new JsonProcessor() {
            @Override
            String getJson() {
                InputStream inputStream = getResources().openRawResource(R.raw.initial_json);

                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                StringBuilder text = new StringBuilder();

                try {
                    while (( line = buffreader.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                } catch (IOException e) {
                    return null;
                }
                return text.toString();
            }

            @Override
            void onReadComplete() {
                DBAdapter db = DBAdapter.getOpenedInstance(MainActivity.this);
                for (String s: genres) {
                    db.addGenre(new Genre(s));
                }
                for (Track t: tracks) {
                    db.addTrack(t);
                }
                updateData();
            }

            @Override
            void onReadFail() {
                Log.e(LOG_TAG, "Failed to load JSON on first run");
            }
        };

        prefs.edit()
             .putBoolean("firstRunComplete", true)
             .apply();

        Log.i(LOG_TAG, "First run complete");
    }

    private void updateData() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.flContainer);
        if (fragment instanceof PlaylistFragment) {
            ((PlaylistFragment)fragment).forceInvalidate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PlaylistFragment.REQUEST_ADD_PLAYLIST)
            updateData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstRun();

        getFragmentManager().
                beginTransaction().add(R.id.flContainer, TracksFragment.newInstance(-1)).commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            finish();
    }
}
