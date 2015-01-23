package ru.ya.exam2;

/**
 * Created by vanya on 23.01.15.
 */
public class OneSong {
    private String id;
    private String singer;
    private String title;;
    private String year;
    private String url;
    private String duration;
    private String population;
    private String genres;

    public OneSong(String id, String singer, String title, String year, String url, String duration, String population, String genres) {
        this.id = id;
        this.singer = singer;
        this.title = title;
        this.year = year;
        this.url = url;
        this.duration = duration;
        this.population = population;
        this.genres = genres;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }

    public String getPopulation() {
        return population;
    }

    public String getGenres() {
        return genres;
    }
}
