package ru.ifmo.md.exam1.fragment;


import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.ifmo.md.exam1.R;
import ru.ifmo.md.exam1.data.MyProvider;

/**
 * Created by german on 22.01.15.
 */
public class PlaylistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String PLAYLIST_BUNDLE_ID = "playlist_id";

    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.playlist_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.song_list);
        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_2,
                null,
                new String[] {
                        MyProvider.SONG_NAME,
                        MyProvider.SONG_ARTIST
                },
                new int[] {
                        android.R.id.text1,
                        android.R.id.text2
                }, 0
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setMessage(
                        "Artist: " + c.getString(c.getColumnIndexOrThrow(MyProvider.SONG_ARTIST)) + "\n"
                        + "Name: " + c.getString(c.getColumnIndexOrThrow(MyProvider.SONG_NAME)) + "\n"
                        + "Duration: " + c.getString(c.getColumnIndexOrThrow(MyProvider.SONG_DUR)) + "\n"
                        + "Year: " + c.getInt(c.getColumnIndexOrThrow(MyProvider.SONG_YEAR)) + "\n"
                        + "Link: " + c.getString(c.getColumnIndexOrThrow(MyProvider.SONG_URL))
                ).create();
                dialog.show();

            }
        });
        getLoaderManager().initLoader(1, getArguments(), this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int plId = args.getInt(PLAYLIST_BUNDLE_ID);
        return new CursorLoader(
                getActivity(),
                MyProvider.PLAYLIST_CONTENT,
                null,
                MyProvider.PLAYLIST_PL_ID + " = ? ",
                new String[] {
                        String.valueOf(plId)
                }, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        int cnt = data.getCount();
        String selection = "";
        String[] selectionArgs = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            if (i != cnt - 1) {
                selection += MyProvider.SONG_ID + " = ? OR ";
            } else {
                selection += MyProvider.SONG_ID + " = ?";
            }
            selectionArgs[i] = data.getString(data.getColumnIndexOrThrow(MyProvider.PLAYLIST_SONG_ID));
            data.moveToNext();
        }
        Cursor c = getActivity().getContentResolver().query(
                MyProvider.SONGS_CONTENT,
                null,
                selection,
                selectionArgs,
                MyProvider.SONG_POP + " desc"
        );
        adapter.swapCursor(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
