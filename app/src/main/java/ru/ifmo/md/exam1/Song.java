package ru.ifmo.md.exam1;

import java.util.ArrayList;

public class Song {

    private String artist;
    private String name;
    private String[] genres;
    private int popularity;
    private int year;

    Song(String artist, String name, String[] genres, int popularity, int year) {
        this.artist = artist;
        this.name = name;
        this.genres = genres;
        this.popularity = popularity;
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public String[] getGenres() {
        return genres;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getYear() {
        return year;
    }
}
