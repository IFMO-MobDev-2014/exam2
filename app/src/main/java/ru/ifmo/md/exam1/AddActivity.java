package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.InputStream;
import java.util.ArrayList;

import ru.ifmo.md.exam1.db.MyContentProvider;
import ru.ifmo.md.exam1.db.PlayBase;
import ru.ifmo.md.exam1.db.SongsBase;

/**
 * Created by Яна on 23.01.2015.
 */
public class AddActivity extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void onAdd(View view) {
        String name = ((EditText)findViewById(R.id.editText3)).getText().toString();
        String artist = ((EditText)findViewById(R.id.editText)).getText().toString();
        String year = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String ans = "";

        Cursor list = getContentResolver()
                .query(MyContentProvider.QUERY,
                       new String[]{
                               SongsBase.ARTIST, SongsBase.YEAR},
                               SongsBase.ARTIST + "=? AND " + SongsBase.YEAR + "=?",
                               new String[]{artist, year}, null);

        if(list != null) {
            list.moveToFirst();
            while (!list.isLast()) {
                ans += list.getInt(list.getColumnIndex(SongsBase._ID)) + " ";
            }
            list.close();

            ContentValues values = new ContentValues();
            values.put(PlayBase.NAME, name);
            values.put(PlayBase.SONGS, ans);

            getContentResolver().insert(MyContentProvider.QUERY_PLAY, values);
        }
        finish();
    }
}
