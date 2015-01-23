package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Амир on 23.01.2015.
 */

public class MusicLoadService extends IntentService {

    public static final String ON_LOAD_FINISHED_BROADCAST = "Load finished";
    public static final String ON_LOAD_FAILED_BROADCAST = "Load failed";

    public MusicLoadService() {
        super("LoadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.d("WAS", "HERE");
            InputStream is = getApplicationContext().getAssets().open("music.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String c = br.readLine();
            String json = "";
            while (c != null) {
                json += c;
                c = br.readLine();
            }
            Log.d("WAS", json);
            JSONArray songs = new JSONArray(json);
            for (int i = 0; i < songs.length(); i++) {
                JSONObject curobj = songs.getJSONObject(i);
                String cur = curobj.getString("name");
                //Log.d("asd", cur);
                String name = cur.substring(0, cur.indexOf(" – "));
                String artist = cur.substring(cur.indexOf(" – ") + 3, cur.length());
                int year = curobj.getInt("year");
                String genres = "";
                JSONArray gen = curobj.getJSONArray("genres");
                for (int j = 0; j < gen.length(); j++) {
                    genres += gen.getString(j);
                }
                ContentValues cv = new ContentValues();
                cv.put(DBHelper.SONGS_COLUMN_NAME, name);
                cv.put(DBHelper.SONGS_COLUMN_ARTIST, artist);
                cv.put(DBHelper.SONGS_COLUMN_GENRES, genres);
                cv.put(DBHelper.SONGS_COLUMN_YEAR, year);
                getContentResolver().insert(DBContentProvider.SONGS, cv);
            }
            Log.d("WAS", "HEREasdasdasdasd");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
