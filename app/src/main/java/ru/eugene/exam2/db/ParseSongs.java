package ru.eugene.exam2.db;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import ru.eugene.exam2.R;
import ru.eugene.exam2.items.PlayList;
import ru.eugene.exam2.items.Song;

/**
 * Created by eugene on 1/23/15.
 */
public class ParseSongs extends IntentService {
    public static final String NOTIFICATION = "ru.eugene.exam2.db";
    private Intent result;
    public static String RESULT = "result";

    public ParseSongs() {
        super("ParseSongs");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Song> songs = readMessageArray();
        ContentValues[] values = new ContentValues[songs.size()];
        Log.e("LOG", "values.size() " + values.length);
        int i = 0;
        for (Song song : songs) {
            values[i++] = song.generateContentValues();
        }
        Log.e("LOG", "after for");

        PlayList playList = new PlayList();
        playList.setName("all songs");
        playList.setDate(getCurDate());
        Log.e("LOG", "before bulkinsert");
        getContentResolver().bulkInsert(SongsProvider.CONTENT_URI_SONG, values);
        Log.e("LOG", "before insert");
        getContentResolver().insert(SongsProvider.CONTENT_URI_PLAY_LIST, playList.generateContentValues());
    }

    private String getCurDate() {
        Long curMillis = System.currentTimeMillis();
        Date curDate = new Date(curMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        formatter.setTimeZone(cal.getTimeZone());

        return formatter.format(curDate);
    }

    private ArrayList<Song> readMessageArray() {
        Log.e("LOG", "readMessage");
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.music);
        String text = convertStreamToString(is);
        ArrayList<Song> songItems = new ArrayList<>();
        try {
            JSONArray reader = new JSONArray(text);
            int curId = 1;
            for (int i = 0; i < reader.length(); i++) {

                JSONObject curObject = reader.getJSONObject(i);

                String name = curObject.getString("name");
                int idArtist = name.indexOf("–");
                String artist = name.substring(0, idArtist - 1);
                name = name.substring(idArtist + 1);

                String url = curObject.getString("url");
                String duration = curObject.getString("duration");
                int popularity = curObject.getInt("popularity");
                int year = curObject.getInt("year");

                JSONArray genres = curObject.getJSONArray("genres");
                for (int j = -1; j < genres.length(); j++) {
                    Song curSong = new Song();
                    curSong.setGenres("");
                    if (j != -1) {
                        curSong.setGenres(genres.getString(j));
                    }
                    curSong.setId2(curId++);
                    curSong.setName(name);
                    curSong.setArtist(artist);
                    curSong.setUrl(url);
                    curSong.setDuration(duration);
                    curSong.setPopularity(popularity);
                    curSong.setYear(year);
                    songItems.add(curSong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songItems;
    }

    static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
