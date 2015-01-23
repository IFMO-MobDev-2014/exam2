package ru.ifmo.md.exam2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class YearAdapter extends BaseAdapter {
    private final Context context;

    public YearAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2020;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(context);
        }
        if (position == 2015) {
            ((TextView) convertView).setText("----");
        } else if (position > 2015) {
            position--;
            ((TextView) convertView).setText(String.format("%04d", position));
        } else {
            ((TextView) convertView).setText(String.format("%04d", position));
        }

        return convertView;
    }
}
