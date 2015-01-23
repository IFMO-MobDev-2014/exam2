package ru.ifmo.md.exam1;

/**
 * Created by timur on 23.01.15.
 */
public class Playlist {
    private String[] authors;
    private String name;
    private String[] janres;
    private int[] years;

    public Playlist(String[] authors, String name, String[] janres, int[] years) {
        this.authors = authors;
        this.name = name;
        this.janres = janres;
        this.years = years;
    }

    public String[] getAuthors() {
        return authors;
    }

    public int[] getYears() {
        return years;
    }

    public String[] getJanres() {
        return janres;
    }

    public String getName() {
        return name;
    }

    public void setSongsId(String[] authors) {
        this.authors = authors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJanres(String[] janres) {
        this.janres = janres;
    }

    public void setYears(int[] years) {
        this.years = years;
    }
}
