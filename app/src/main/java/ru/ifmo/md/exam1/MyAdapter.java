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

public class MyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List <Song> tasks;

    public MyAdapter(List <Song> tasks, Context context) {
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
            convertView = inflater.inflate(R.layout.item, null, false);
        }
        TextView artist = (TextView) convertView.findViewById(R.id.artist);
        String artistStr = "Artist: " + tasks.get(position).getArtist();
        artist.setText(artistStr);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        String nameStr = "Song:" + tasks.get(position).getName();
        name.setText(nameStr);

        TextView genres = (TextView) convertView.findViewById(R.id.genres);
        String[] genresArr = tasks.get(position).getGenres();
        String genresStr = "Genres: ";
        for (int i = 0; i < genresArr.length; i++) {
            if (i != genresArr.length - 1) {
                genresStr += genresArr[i] + ", ";
            } else {
                genresStr += genresArr[i];
            }
        }
        genres.setText(genresStr);

        TextView popularity = (TextView) convertView.findViewById(R.id.popularity);
        Integer popularityInt = tasks.get(position).getPopularity();
        String popularityStr = "Popularity: " + Integer.toString(popularityInt);
        popularity.setText(popularityStr);

        TextView year = (TextView) convertView.findViewById(R.id.year);
        Integer yearInt = tasks.get(position).getYear();
        String yearStr = "Year: " + Integer.toString(yearInt);
        year.setText(yearStr);

        return convertView;
    }

    public void setSongs(List<Song> tasks) {
        this.tasks = tasks;
    }


}