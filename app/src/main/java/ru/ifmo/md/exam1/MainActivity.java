package ru.ifmo.md.exam1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    Button addPlaylist;
    public static final String APP_PREFERENCES = "my settings";
    public static final String APP_PREFERENCES_FIRST_LAUNCH = "first launch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mSettings;
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!mSettings.contains(APP_PREFERENCES_FIRST_LAUNCH)) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(APP_PREFERENCES_FIRST_LAUNCH, 1);
            editor.apply();
            Intent intent = new Intent(this, MusicLoadService.class);
            startService(intent);
        }
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowPlaylist.class);
                intent.putExtra("Playlist", ((PlayList) listView.getAdapter().getItem(position)).name);
                startActivity(intent);
            }
        });
        addPlaylist = (Button) findViewById(R.id.button);
        getLoaderManager().initLoader(0, null, this);
        this.registerReceiver(onFinishDownload, finishFilter);
    }

    public void onClickAddPlaylist(View v) {
        final View dialog = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = ((EditText) dialog.findViewById(R.id.name)).getText().toString();
                CheckBox ifartist = (CheckBox) dialog.findViewById(R.id.checkBox);
                CheckBox ifyear = (CheckBox) dialog.findViewById(R.id.checkBox2);
                CheckBox ifgenres = (CheckBox) dialog.findViewById(R.id.checkBox3);
                String selection = "";
                String[] genresArray = new String[]{"-", "-", "-", "-", "-"};
                if (ifartist.isChecked()) {
                    EditText text = (EditText) dialog.findViewById(R.id.editText);
                    selection = DBHelper.SONGS_COLUMN_ARTIST + " = \'" + text.getText().toString() + "\'";
                }
                if (ifyear.isChecked()) {
                    EditText text = (EditText) dialog.findViewById(R.id.editText2);
                    if (!selection.equals("")) {
                        selection += " and ";
                    }
                    selection += DBHelper.SONGS_COLUMN_YEAR + " = " + text.getText().toString() + "";
                }
                if (ifgenres.isChecked()) {
                    if (((CheckBox) dialog.findViewById(R.id.Alternative)).isChecked()) {
                        genresArray[0] = "Alternative";
                    }
                    if (((CheckBox) dialog.findViewById(R.id.Blues)).isChecked()) {
                        genresArray[0] = "Blues";
                    }
                    if (((CheckBox) dialog.findViewById(R.id.Jazz)).isChecked()) {
                        genresArray[0] = "Jazz";
                    }
                    if (((CheckBox) dialog.findViewById(R.id.Raggie)).isChecked()) {
                        genresArray[0] = "Raggie";
                    }
                    if (((CheckBox) dialog.findViewById(R.id.Soul)).isChecked()) {
                        genresArray[0] = "Soul";
                    }

                }
                Intent intent = new Intent(MainActivity.this, CreatePlayListService.class);
                intent.putExtra("name", name);
                intent.putExtra("selection", selection);
                intent.putExtra("genres", genresArray);
                startService(intent);
            }
        });
        builder.create().show();

    }

    IntentFilter finishFilter = new IntentFilter(CreatePlayListService.FINISH);
    private BroadcastReceiver onFinishDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getLoaderManager().restartLoader(0, null, MainActivity.this);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {DBHelper.PLAYLISTS_COLUMN_NAME};
        return new CursorLoader(this, DBContentProvider.PLAYLISTS, projection, null, null, null);
    }

    public void setListView(Cursor cursor) {
        cursor.moveToFirst();
        ArrayList<PlayList> items = new ArrayList<PlayList>();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.PLAYLISTS_COLUMN_NAME));
            cursor.moveToNext();
            items.add(new PlayList(name));
        }
        cursor.close();
        MyAdapter adapter = new MyAdapter(items, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setListView(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
