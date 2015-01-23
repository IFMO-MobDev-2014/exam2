package ru.ifmo.md.exam1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
// {"name":"Mapei – Don't Wait",
// "url":"https://cs1-46v4.vk-cdn.net/p23/53f922c0d2cec9.mp3",
// "duration":"3:35",
// "popularity":76,
// "genres":["Blues","Alternative","Soul"],
// "year":2009}

public class AddPlayListActivity extends ActionBarActivity {
    ArrayList<Track> items;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        Intent intent = getIntent();
        items = intent.getParcelableArrayListExtra("list");
        pos = intent.getIntExtra("pos", 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
