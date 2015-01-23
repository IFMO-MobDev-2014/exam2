package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class NewListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_list, menu);
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

    public void addBtn(View view) {
        EditText txt = (EditText)findViewById(R.id.editArtist);
        String artist = txt.getText().toString();
        txt = (EditText)findViewById(R.id.editYear);
        Integer year = Integer.parseInt(txt.getText().toString());
        txt = (EditText) findViewById(R.id.editName);
        String listName = txt.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TRACK_KEY_NAME, listName);
        cv.put(DBHelper.KEY_RAW_TYPE, 2);
        

    }
}
