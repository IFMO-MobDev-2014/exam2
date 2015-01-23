package ru.ifmo.md.exam1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vadim on 23/01/15.
 */
public class PlaylistAdapter extends ArrayAdapter<String> {
    public PlaylistAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.playlist_item_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.playlist_display_name);
        textView.setText(getItem(position));
        return rowView;
    }
}
