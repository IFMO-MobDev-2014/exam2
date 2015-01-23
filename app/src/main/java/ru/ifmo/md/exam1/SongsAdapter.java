package ru.ifmo.md.exam1;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 23.01.2015.
 */
public class SongsAdapter extends BaseAdapter {

    List<Song> data;

    public SongsAdapter() {
        data = new ArrayList<>();
    }

    public void add(Song s) {
        data.add(s);
    }

    public void clear() {
        data.clear();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Song getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if (view == null) {
            v = android.view.LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_layout, viewGroup, false);
        } else {
            v = view;
        }
        Song current = getItem(i);
        ((TextView)v.findViewById(R.id.song_name)).setText(current.getName());
        ((TextView)v.findViewById(R.id.song_artist)).setText(current.getArtist());
        //((TextView)v.findViewById(R.id.song_duration_)).setText(current.getDuration());
        //((TextView)v.findViewById(R.id.song_year)).setText(current.getYear());

        return v;
    }
}
