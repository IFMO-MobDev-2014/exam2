package ru.ifmo.md.exam1;

import android.provider.MediaStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ifmo.md.exam1.provider.playlists.PlaylistsContentValues;
import ru.ifmo.md.exam1.provider.playlists.PlaylistsCursor;

/**
 * Created by izban on 23.01.15.
 */
public class Playlist {
    String name;

    Playlist() {}

    Playlist(PlaylistsCursor cursor) {
        name = cursor.getName();
    }

    public PlaylistsContentValues getContentValues() {
        PlaylistsContentValues contentValues = new PlaylistsContentValues();
        contentValues.putName(name);
        return contentValues;
    }

    @Override
    public String toString() {
        return name;
    }
}
