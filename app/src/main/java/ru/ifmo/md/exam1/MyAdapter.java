package ru.ifmo.md.exam1;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Амир on 23.01.2015.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<PlayList> items;
    Context context;

    public MyAdapter(ArrayList<PlayList> items, Context context) {
        super();
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_view, null);
        }
        PlayList t = items.get(i);

        TextView title = (TextView) view.findViewById(R.id.textView);

        title.setText(t.name);

        return view;
    }
}
