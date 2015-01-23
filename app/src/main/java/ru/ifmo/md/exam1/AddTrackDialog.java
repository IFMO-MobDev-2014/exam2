package ru.ifmo.md.exam1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static ru.ifmo.md.exam1.ItemsListProvider.ARTIST;
import static ru.ifmo.md.exam1.ItemsListProvider.NAME;

/**
 * Created by dimatomp on 23.01.15.
 */
public class AddTrackDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    CursorAdapter adapter;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Uri.parse(ItemsListProvider.FETCH_SONGS), null, null, null, null);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListView listView = new ListView(getActivity());
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null,
                new String[]{NAME, ARTIST}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getContentResolver().insert(Uri.parse(ItemsListProvider.INSERT_SONG +
                        "?songId=" + id + "&playlistId="
                        + getActivity().getIntent().getData().getQueryParameter("playlistId")), null);
                getActivity().getLoaderManager().restartLoader(0, null, (LoaderManager.LoaderCallbacks) getActivity());
                dismiss();
            }
        });
        return builder.setTitle("Add track").setView(listView).create();
    }
}
