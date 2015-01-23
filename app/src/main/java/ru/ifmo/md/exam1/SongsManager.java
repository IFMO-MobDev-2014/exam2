package ru.ifmo.md.exam1;

import android.app.Activity;
import android.content.Context;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timur on 23.01.15.
 */
public class SongsManager {
    private List<Song> songs;
    private Context context;

    public SongsManager(Activity activity) {
        this.songs = new ArrayList<>();
        this.context = activity.getApplicationContext();
    }
}
