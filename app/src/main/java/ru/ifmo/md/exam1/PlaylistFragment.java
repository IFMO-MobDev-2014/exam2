package ru.ifmo.md.exam1;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import ru.ifmo.md.exam1.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class PlaylistFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Rename and change types of parameters
    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaylistFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fillData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_playlists, menu);
    }

    public static final int REQUEST_ADD_PLAYLIST = 100;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivityForResult(
                    new Intent(getActivity(), AddPlaylistActivity.class), REQUEST_ADD_PLAYLIST);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        forceInvalidate();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, TracksFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    public void forceInvalidate() {
        getLoaderManager().restartLoader(0, null, this);
    }

    private void fillData() {
        getLoaderManager().initLoader(0, null, this);
        if (getListAdapter() == null)
            setListAdapter(new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, null,
                    new String[]{DBAdapter.KEY_PLAYLISTS_NAME},
                    new int[]{android.R.id.text1}, 0));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                TracksContentProvider.PLAYLISTS_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((CursorAdapter) getListAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((CursorAdapter) getListAdapter()).swapCursor(null);
    }
}
