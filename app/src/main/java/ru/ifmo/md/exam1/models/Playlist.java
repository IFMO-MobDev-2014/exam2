package ru.ifmo.md.exam1.models;

import java.util.ArrayList;

/**
 * Created by vadim on 23/01/15.
 */
public class Playlist {
    public final String id;
    public final String name;
    public final ArrayList<Song> songs;

    public Playlist(String id, String name, ArrayList<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }
}
