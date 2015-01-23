package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyService extends IntentService {

    public MyService() {
        super("service");
    }

    public class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getContentResolver().delete(MyContentProvider.SONGS_CONTENT_URI, null, null);
        InputStream is = getResources().openRawResource(R.raw.music);
        String songs = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            FastScanner in = new FastScanner(is);

            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                songs += s;
            }
        } catch (Exception e) {
            Intent response = new Intent();
            response.setAction("RESPONSE");
            response.addCategory(Intent.CATEGORY_DEFAULT);
            response.putExtra("ok", -1);
            sendBroadcast(response);
        }
        try {
            //JSONObject jsonObject = new JSONObject(songs);
            JSONArray jsonArray = new JSONArray(songs);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject current = jsonArray.getJSONObject(i);
                String name1 = current.getString("name");
                String[] much = name1.split("–");
                String artist = much[0];
                String name = "";
                for (int j = 1; j < much.length; j++) {
                    name += much[j];
                }
                int population = Integer.parseInt(current.getString("popularity"));
                int year = Integer.parseInt(current.getString("year"));
                String genres = "";
                JSONArray jsonGenres = current.getJSONArray("genres");
                for (int j = 0; j < jsonGenres.length(); j++) {
                    //JSONObject genre = jsonGenres.getJSONObject(j);
                    String genreStr = jsonGenres.getString(j);
                    genres += genreStr + ",";
                }
                ContentValues cv = new ContentValues();
                cv.put(SQLiteHelper.ARTIST, artist);
                cv.put(SQLiteHelper.NAME, name);
                cv.put(SQLiteHelper.GENRES, genres);
                cv.put(SQLiteHelper.POPULARITY, population);
                cv.put(SQLiteHelper.YEAR, year);
                getContentResolver().insert(MyContentProvider.SONGS_CONTENT_URI, cv);
            }
            Intent response = new Intent();
            response.setAction("RESPONSE");
            response.addCategory(Intent.CATEGORY_DEFAULT);
            response.putExtra("ok", 1);
            sendBroadcast(response);
        } catch (Exception e) {
            Intent response = new Intent();
            response.setAction("RESPONSE");
            response.addCategory(Intent.CATEGORY_DEFAULT);
            response.putExtra("ok", -1);
            sendBroadcast(response);
        }
    }
}
