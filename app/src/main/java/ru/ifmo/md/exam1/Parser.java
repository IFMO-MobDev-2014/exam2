package ru.ifmo.md.exam1;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Яна on 23.01.2015.
 */
public class Parser {
    public static class Item {
        String name;
        String artist;
        String duration;
        int year;
        int popularity;

        Item(String name, String artist, String duration, int year, int popularity) {
            this.name = name;
            this.artist = artist;
            this.duration = duration;
            this.year = year;
            this.popularity = popularity;
        }
    }

    private static String streamToString(InputStream s) {
        Scanner scn = new Scanner(s);
        scn.useDelimiter("\\A");
        return scn.next();
    }


    public static ArrayList<Item> parse(InputStream inputStream) {
        ArrayList<Item> answer = new ArrayList<>();
        try {
            JSONArray res = new JSONArray(streamToString(inputStream));
            for (int i = 0; i < res.length(); i++) {
                String name = res.getJSONObject(i).getString("name");

                int j = 0;
                while (name.charAt(j++) != '–');

                String artist = name.substring(0, j - 1);
                name = name.substring(j + 1, name.length());

                String duration = res.getJSONObject(i).getString("duration");
                int year = res.getJSONObject(i).getInt("year");
                int popularity = res.getJSONObject(i).getInt("popularity");
                answer.add(new Item(name, artist, duration, year, popularity));
            }

        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
        return answer;
    }
}
