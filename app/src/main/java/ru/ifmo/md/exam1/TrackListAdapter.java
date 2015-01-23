package ru.ifmo.md.exam1;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TrackListAdapter extends ArrayAdapter<Track> {
    public TrackListAdapter(Activity activity, int id, ArrayList<Track> tracks) {
        super(activity, id, tracks);
    }
}
