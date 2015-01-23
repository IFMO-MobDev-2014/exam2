package ru.ifmo.md.exam1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vi34 on 23.01.15.
 */
public class MyAdapter extends BaseAdapter {
    public List<Track> data;

    public MyAdapter(List<Track> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.list_text1);
        textView.setText(data.get(position).name);
        getItem(position);
        return v;
    }
}