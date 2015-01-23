package ru.ifmo.md.exam1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class JsonProcessor {

    List<Track> tracks = new ArrayList<>();
    HashSet<String> genres = new HashSet<>();

    public JsonProcessor() {
        try {
            JSONArray a = new JSONArray(getJson());
            for (int i = 0; i < a.length(); ++i) {
                JSONObject o = a.getJSONObject(i);
                String fullName = o.getString("name");
                String[] parts = fullName.split("\\s+[-,–]\\s+");
                if (parts.length < 2)
                    Log.e("ru.ifmo.md.exam1", fullName + " is not a valid track title");
                String artist = parts[0];
                String name = parts[1];
                List<String> genreNamesForTrack = new ArrayList<>();
                int year = o.getInt("year");
                int popularity = o.getInt("popularity");
                JSONArray g = o.getJSONArray("genres");
                for (int j = 0; j < g.length(); ++j) {
                    String genreName = g.getString(j);
                    if (!genres.contains(genreName)) {
                        genres.add(genreName);
                    }
                    genreNamesForTrack.add(genreName);
                }
                List<Genre> genresForTrack = new ArrayList<>();
                for (String n: genreNamesForTrack) {
                    genresForTrack.add(new Genre(n));
                }
                tracks.add(new Track(name, artist, year, popularity, genresForTrack));
            }
            onReadComplete();
        } catch (JSONException e) {
            e.printStackTrace();
            onReadFail();
        }
    }

    abstract String getJson();
    abstract void onReadComplete();
    abstract void onReadFail();
}
