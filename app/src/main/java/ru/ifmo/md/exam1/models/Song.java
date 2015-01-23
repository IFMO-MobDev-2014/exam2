package ru.ifmo.md.exam1.models;

import java.util.List;

/**
 * Created by vadim on 23/01/15.
 */
public class Song {
    public final String id;
    public final String name;
    public final String url;
    public final int duration;
    public final int popularity;
    public final int year;
    public final List<Genre> genres;

    public Song(String id, String name, String url, int duration, int popularity, int year, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.popularity = popularity;
        this.year = year;
        this.genres = genres;
    }
}
