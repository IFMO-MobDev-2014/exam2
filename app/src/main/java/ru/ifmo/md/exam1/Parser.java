package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import ru.ifmo.md.exam1.provider.song.SongColumns;

/**
 * Created by izban on 23.01.15.
 */
public class Parser {
    public static void parse(Context context, SQLiteDatabase db) {
        Log.i("", "parsing started");
        try {
            InputStream input = context.getAssets().open("music.txt");
            Scanner in = new Scanner(input);
            JSONArray array = new JSONArray(in.nextLine());
            TreeSet<String> set = new TreeSet<String>();

            ContentValues[] cv = new ContentValues[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                cv[i] = new Song(item).getContentValues().values();
            }

            db.delete(SongColumns.TABLE_NAME, null, null);
            db.beginTransaction();
            try {
                for (ContentValues v : cv) {
                    long id = db.insert(SongColumns.TABLE_NAME, null, v);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }

            Log.i("", "parsed ok");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.i("", "parsing failed");
        }
    }
}
