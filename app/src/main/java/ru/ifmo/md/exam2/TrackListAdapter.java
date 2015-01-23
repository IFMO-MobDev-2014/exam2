package ru.ifmo.md.exam2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class TrackListAdapter extends BaseAdapter {
    private final Track[] tracks;
    private final Context context;

    public TrackListAdapter(Track[] tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tracks.length;
    }

    @Override
    public Object getItem(int position) {
        return tracks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.track_row, null);
        }
        TextView titleView = (TextView) convertView.findViewById(R.id.track_name);
        titleView.setText(tracks[position].getName());

        return convertView;
    }
}
