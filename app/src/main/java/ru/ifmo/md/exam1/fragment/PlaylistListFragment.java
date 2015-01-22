package ru.ifmo.md.exam1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.ifmo.md.exam1.MainActivity;
import ru.ifmo.md.exam1.R;
import ru.ifmo.md.exam1.data.MyProvider;

/**
 * Created by german on 22.01.15.
 */
public class PlaylistListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_list_fragment, container, false);

        view.findViewById(R.id.add_year_playlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v.getId());
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.playlist_list);
        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                new String[] {
                        MyProvider.PLAYLISTS_NAME
                },
                new int[] {
                        android.R.id.text1
                }, 0
        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);

                int plId = cursor.getInt(cursor.getColumnIndexOrThrow(MyProvider.PLAYLISTS_ID));
                Bundle args = new Bundle();
                args.putInt(PlaylistFragment.PLAYLIST_BUNDLE_ID, plId);
                ((MainActivity) getActivity()).getNavigationHelper().openFragment(PlaylistFragment.class, args, true);

                Log.d(getClass().getName(), "Selected playlist id, name: " + plId
                                                                           + cursor.getString(cursor.getColumnIndexOrThrow(MyProvider.PLAYLISTS_NAME)));

            }
        });
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    public void showDialog(final int resId) {
        final View layout = View.inflate(getActivity(), R.layout.add_playlist_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = ((EditText) layout.findViewById(R.id.edit)).getText().toString();
                        if (resId == R.id.add_year_playlist) {

                            Log.d(getClass().getName(), "Create playlist by year " + value);
                        }
                    }
                }).create();
        dialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(getClass().getName(), "Loader is creating");
        return new CursorLoader(
                getActivity(),
                MyProvider.PLAYLISTS_CONTENT,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
