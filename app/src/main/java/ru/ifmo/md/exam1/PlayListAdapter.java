package ru.ifmo.md.exam1;

import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Алексей on 23.01.2015.
 */
public class PlayListAdapter extends BaseAdapter {

    private List<PlayList> data;

    public PlayListAdapter() {
        data = new ArrayList<>();
    }

    public void add(PlayList x) {
        data.add(x);
    }

    public void clear() {
        data.clear();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PlayList getItem(int position) {
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
            v = android.view.LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.play_list_layout, viewGroup, false);
        } else {
            v = view;
        }
        PlayList current = getItem(i);
        ((TextView)v.findViewById(R.id.pl_name)).setText(current.getName());
        ((TextView)v.findViewById(R.id.pl_count)).setText(current.getSongsId().size()+ " песен");
        return v;

    }
}
