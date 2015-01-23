package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

/**
 * Created by Амир on 23.01.2015.
 */
public class CreatePlayListService extends IntentService {

    public static final String FINISH = "FINISH";

    public CreatePlayListService() {
        super("PlayListCreator");
    }

    private boolean check(String q, String[] w) {
        for (int i = 0; i < w.length; i++) {
            if (q.contains(w[i])) {
                return true;
            }
        }
        return false;
    }

    private int addPlayList(String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PLAYLISTS_COLUMN_NAME, name);
        //cv.put(DBHelper);
        getContentResolver().insert(DBContentProvider.PLAYLISTS, cv);
        Cursor cursor = getContentResolver().query(DBContentProvider.PLAYLISTS, new String[]{DBHelper.PLAYLISTS_COLUMN_ID},
                DBHelper.PLAYLISTS_COLUMN_NAME + " = \'" + name + "\'", null, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(DBHelper.PLAYLISTS_COLUMN_ID));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String playlistName = intent.getStringExtra("name");
        int playListId = addPlayList(playlistName);
        String selection = intent.getStringExtra("selection");
        String[] genres = intent.getStringArrayExtra("genres");
        Cursor cursor = getContentResolver().query(DBContentProvider.SONGS, new String[]{DBHelper.SONGS_COLUMN_ID, DBHelper.SONGS_COLUMN_GENRES},
                selection, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String sgenres = cursor.getString(cursor.getColumnIndex(DBHelper.SONGS_COLUMN_GENRES));
            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.SONGS_COLUMN_ID));
            if (check(sgenres, genres)) {
                ContentValues cv = new ContentValues();
                cv.put(DBHelper.SONGSINPLAYLIST_COLUMN_SONGID, id);
                cv.put(DBHelper.SONGSINPLAYLIST_COLUMN_PLAYLISTID, playListId);
                getContentResolver().insert(DBContentProvider.SONGSINPLAYLISTS, cv);
            }
            cursor.moveToNext();
        }
        cursor.close();
        Intent intentResponse = new Intent();
        intentResponse.setAction("FINISH");
        sendBroadcast(intentResponse);
    }
}
