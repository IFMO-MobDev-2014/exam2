package ru.ifmo.md.exam1;

/**
 * Created by timur on 23.01.15.
 */
public class Playlist {
    private int[] songsId;
    private String name;
    private String[] janres;
    private int[] years;

    public Playlist(int[] songsId, String name, String[] janres, int[] years) {
        this.songsId = songsId;
        this.name = name;
        this.janres = janres;
        this.years = years;
    }

    public int[] getSongsId() {
        return songsId;
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

    public void setSongsId(int[] songsId) {
       this.songsId = songsId;
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
