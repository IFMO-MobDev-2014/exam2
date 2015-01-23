package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class PlaylistsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String INIT = "init";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        getLoaderManager().initLoader(0, null, new MyLoaderManager());
        ((ListView) findViewById(R.id.playlist_view)).setAdapter(new PlaylistsAdapter(this, null, true));
        TextView textView = new TextView(this);
        ((ListView) findViewById(R.id.playlist_view)).setEmptyView(textView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.contains(INIT)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(INIT, true);
            editor.apply();
            startService(new Intent(getApplicationContext(), InitializerService.class));
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
            Intent intent = new Intent(this, CreatePlaylistActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String name = data.getStringExtra(PlaylistsTable.TABLE_NAME);
            String[] janre = data.getStringArrayExtra(PlaylistsTable.PLAYLIST_JANRES);
            int[] years = data.getIntArrayExtra(PlaylistsTable.PLAYLIST_YEARS);
            String[] authors = data.getStringArrayExtra(PlaylistsTable.PLAYLIST_AUTHORS);
            int id = data.getIntExtra(PlaylistsTable.COLUMN_ROWID, -1);
            if (id != -1) {
                /*ContentValues contentValues = new ContentValues();
                contentValues.put(PlaylistsTable.PLAYLIST_NAME, name);
                contentValues.put(PlaylistsTable.PLAYLIST_JANRES, janre);
                contentValues.put(PlaylistsTable.PLAYLIST_YEARS, years);
                contentValues.put(PlaylistsTable.PLAYLIST_AUTHORS, authors);
                contentValues.put(PlaylistsTable.COLUMN_ROWID, id);
                getContentResolver().insert(DatabaseContentProvider.URI_CITY_DIR, contentValues);
                getContentResolver().notifyChange(DatabaseContentProvider.URI_CITY_DIR, null);
                Intent intent = new Intent(getApplicationContext(), LoaderService.class);
                intent.putExtra(LoaderService.EXTRA_SINGLE_ID, cityId);
                startService(intent);*/
            }
        }
    }


    private class MyLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new CursorLoader(
                    getApplicationContext(),
                    DatabaseContentProvider.URI_FEED_DIR,
                    new String[]{PlaylistsTable.TABLE_NAME},//....
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> objectLoader, Cursor cursor) {
            ((CursorAdapter) ((ListView) findViewById(R.id.playlist_view)).getAdapter()).swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> objectLoader) {
            ((CursorAdapter) ((ListView) findViewById(R.id.playlist_view)).getAdapter()).swapCursor(null);
        }
    }
}
