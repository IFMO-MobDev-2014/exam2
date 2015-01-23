package ru.ifmo.md.exam2;

import ru.ifmo.md.exam2.provider.track.TrackContentValues;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class Track {
    private String name;
    private String author;
    private int year;
    private GenresMask genresMask;

    public Track() {
    }

    public Track(String name, String author, int year, GenresMask genresMask) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.genresMask = genresMask;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public GenresMask getGenresMask() {
        if (genresMask == null) {
            genresMask = new GenresMask();
        }
        return genresMask;
    }

    public void addGenre(GenresMask.Genre genre) {
        if (genresMask == null) {
            genresMask = new GenresMask();
        }

        genresMask.add(genre);
    }

    public void setGenresMask(GenresMask genresMask) {
        this.genresMask = genresMask;
    }

    public TrackContentValues toContentValues() {
        TrackContentValues contentValues = new TrackContentValues();
        contentValues.putTitle(name);
        contentValues.putAuthor(author);
        contentValues.putYear(year);
        contentValues.putGenresMask(getGenresMask().getMask());
        return contentValues;
    }
}
