package ru.ifmo.md.exam1;

/**
 * Created by timur on 23.01.15.
 */
public class Song {
    private String name;
    private String author;
    private int length;
    private String[] janres;
    private int year;
    private int popularity;

    public Song(String name, String author, int length, String[] janres, int year, int popularity) {
        this.name = name;
        this.author = author;
        this.length = length;
        this.janres = janres;
        this.year = year;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String[] getJanres() {
        return janres;
    }

    public void setJanres(String[] janres) {
        this.janres = janres;
    }

    public int getYears() {
        return year;
    }

    public void setYears(int year) {
        this.year = year;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
