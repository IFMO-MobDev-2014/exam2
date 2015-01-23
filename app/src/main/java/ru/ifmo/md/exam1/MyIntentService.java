package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class MyIntentService extends IntentService {
    public MyIntentService()
    {
        super("MyService");
    }
    protected void onHandleIntent(Intent intent) {
        Cursor cursor = getContentResolver().query(MyProvider.PHOTOS_CONTENT_URI, null, null, null, null);
       if(cursor.getCount() < 10) {
            InputStream is = getResources().openRawResource(R.raw.music);
            String music = new Scanner(is).useDelimiter("\n").next();
            try {
                JSONObject json;
                JSONArray jsonArray = new JSONArray(music);

                ContentValues cv = new ContentValues();
                for(int i = 0; i < jsonArray.length(); ++i) {
                    json = jsonArray.getJSONObject(i);

                    String artist = json.getString("name");
                    int ind = artist.indexOf('–');
                    artist = artist.substring(0, ind - 1);
                    cv.put(DBHelper.TRACK_KEY_NAME, json.getString("name"));
                    cv.put(DBHelper.TRACK_KEY_ARTIST, artist);
                    cv.put(DBHelper.TRACK_KEY_URL, json.getString("url"));
                    cv.put(DBHelper.TRACK_KEY_DURATION, json.getString("duration"));
                    cv.put(DBHelper.TRACK_KEY_POPULARITY, json.getInt("popularity"));
                    cv.put(DBHelper.KEY_RAW_TYPE, 1);
                    cv.put(DBHelper.KEY_LIST, "0");
                    cv.put(DBHelper.TRACK_KEY_YEAR, json.getInt("year"));

                    Uri row = getContentResolver().insert(MyProvider.PHOTOS_CONTENT_URI,cv);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            cursor.close();


        }
    }

}
