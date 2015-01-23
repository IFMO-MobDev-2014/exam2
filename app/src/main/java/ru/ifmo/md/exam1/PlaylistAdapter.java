package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List <String> tasks;

    public PlaylistAdapter(List <String> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.playlist, null, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(tasks.get(position));
        return convertView;
    }

    public void setStr(List<String> tasks) {
        this.tasks = tasks;
    }


}