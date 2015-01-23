package ru.ifmo.md.exam1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ifmo.md.exam1.provider.song.SongColumns;
import ru.ifmo.md.exam1.provider.song.SongContentValues;
import ru.ifmo.md.exam1.provider.song.SongCursor;

/**
 * Created by izban on 23.01.15.
 */
public class Song {
    String artist;
    String song;
    String url;
    String duration;
    String popularity;
    String genres_mask;
    Integer year;

    Song() {}
    Song(JSONObject json) {
        try {
            String name = json.getString("name");
            artist = name.substring(0, name.indexOf('–'));
            song = name.substring(name.indexOf('–') + 1, name.length());
            url = json.getString("url");
            duration = json.getString("duration");
            popularity = json.getString("popularity");

            StringBuilder s = new StringBuilder();
            for (int j = 0; j < Constants.genres.length; j++) {
                s.append('0');
            }
            JSONArray array = json.getJSONArray("genres");
            for (int i = 0; i < array.length(); i++) {
                s.setCharAt(Constants.getGenreIndex(array.getString(i)), '1');
            }

            genres_mask = s.toString();

            year = json.getInt("year");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Song(SongCursor cursor) {
        artist = cursor.getArtist();
        song = cursor.getSong();
        url = cursor.getUrl();
        duration = cursor.getDuration();
        popularity = cursor.getPopularity();
        genres_mask = cursor.getGenresMask();
        year = cursor.getYear();
    }

    public SongContentValues getContentValues() {
        SongContentValues contentValues = new SongContentValues();
        contentValues.putArtist(artist);
        contentValues.putSong(song);
        contentValues.putUrl(url);
        contentValues.putDuration(duration);
        contentValues.putPopularity(popularity);
        contentValues.putGenresMask(genres_mask);
        contentValues.putYear(year);
        return contentValues;
    }

    @Override
    public String toString() {
        return artist + " - " + song;
    }
}
