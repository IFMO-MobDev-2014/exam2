package ru.ifmo.md.exam1;

/**
 * Created by izban on 23.01.15.
 */
public class Constants {
    public static final String[] genres = {"Alternative", "Blues", "Country", "Electronic", "Jazz", "Pop", "Raggie", "Rap", "Rock", "Soul"};

    public static int getGenreIndex(String s) {
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }
}
