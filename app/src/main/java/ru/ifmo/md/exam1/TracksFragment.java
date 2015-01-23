package ru.ifmo.md.exam1;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.app.Notification;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A fragment representing a list of Items.
 * interface.
 */
public class TracksFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_PLAYLIST_ID = "playlistId";

    private long playlistId = -1;

    public static TracksFragment newInstance(long playlistId) {
        TracksFragment fragment = new TracksFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PLAYLIST_ID, playlistId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TracksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            playlistId = getArguments().getLong(ARG_PLAYLIST_ID);
        }
        fillData();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_tracks, menu);
    }

    public static final String PREF_SORT_MODE = "sort";
    public static final String PREF_SORT_MODE_POP = "popularity";
    public static final String PREF_SORT_MODE_DEFAULT = "default";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_playlists) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, PlaylistFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        if (item.getItemId() == R.id.action_sort_default) {
            PreferenceManager
                    .getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putString(PREF_SORT_MODE, PREF_SORT_MODE_DEFAULT)
                    .apply();
            getActivity().invalidateOptionsMenu();
            reload();
        }
        if (item.getItemId() == R.id.action_sort_pop) {
            PreferenceManager
                    .getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putString(PREF_SORT_MODE, PREF_SORT_MODE_POP)
                    .apply();
            getActivity().invalidateOptionsMenu();
            reload();
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        boolean defaultSort = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(PREF_SORT_MODE, PREF_SORT_MODE_DEFAULT).equals(PREF_SORT_MODE_DEFAULT);
        menu.findItem(R.id.action_sort_default).setVisible(!defaultSort);
        menu.findItem(R.id.action_sort_pop).setVisible(defaultSort);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static final int REQUEST_DETAILS = 115;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra(DetailsActivity.EXTRA_ID, id);
        startActivityForResult(i, REQUEST_DETAILS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            reload();
    }

    private void reload() {
        getLoaderManager().restartLoader(0, null, this);
    }

    private void fillData() {
        getLoaderManager().initLoader(0, null, this);
        if (getListAdapter() == null)
            setListAdapter(new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_2, null,
                    new String[]{DBAdapter.KEY_TRACKS_NAME, DBAdapter.KEY_TRACKS_ARTIST},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                playlistId == -1
                        ? TracksContentProvider.TRACKS_URI
                        : ContentUris.withAppendedId(TracksContentProvider.TRACKS_URI, playlistId)
                , null, null, null, null);
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
