package ru.ifmo.md.exam1;

import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 23.01.2015.
 */
public class SongsParser {
    public static List<Song> parse(String s) throws JSONException {
        JSONArray jsonArray = new JSONArray(s);
        List<Song> songs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject song = jsonArray.getJSONObject(i);
            Log.d("PARSING", song.toString());

            String[] nameArt = song.getString("name").split("–");
            Log.d("NAME_SONG", song.getString("name"));
            String artist = nameArt[0];
            String name = nameArt[1];
            String url = song.getString("url");
            String[] dStr = song.getString("duration").split(":");
            int duration = Integer.parseInt(dStr[0])*60 + Integer.parseInt(dStr[1]);
            int pop = song.getInt("popularity");
            int year = song.getInt("year");
            JSONArray a = song.getJSONArray("genres");
            String genre = "";
            for (int j = 0; j < a.length(); j++) {
                if (j != 0) {
                    genre += "|";
                }
                genre += a.getString(j);
            }
            songs.add(new Song(artist, name, duration, pop, genre, year));

        }
        return songs;
    }
}
