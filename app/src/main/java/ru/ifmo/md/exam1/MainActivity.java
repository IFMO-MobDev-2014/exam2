package ru.ifmo.md.exam1;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks <List <Song>> {

    private MyAdapter adapter;
    private ListView view;
    private MyBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter(new ArrayList<Song>(), this);
        view.setAdapter(adapter);
        SharedPreferences sp = getSharedPreferences("my_settings", Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited) {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
            getLoaderManager().initLoader(1, null, MainActivity.this);
        } else {
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("RESPONSE");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, filter);
        /*view = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter(new ArrayList<Song>(), this);
        view.setAdapter(adapter);*/
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SingleSong.class);
                Song current = (Song) adapter.getItem(position);
                intent.putExtra("artist", "Artist: " + current.getArtist());
                intent.putExtra("name", "Song:" + current.getName());
                String[] genres = current.getGenres();
                String resultGenres = "Genres: ";
                for (int i = 0; i < genres.length; i++) {
                    resultGenres += genres[i];
                    if (i != genres.length - 1) {
                        resultGenres += ", ";
                    }
                }
                intent.putExtra("genres", resultGenres);
                intent.putExtra("popularity", "Popularity: " + Integer.toString(current.getPopularity()));
                intent.putExtra("year", "Year: " + Integer.toString(current.getYear()));
                startActivity(intent);
            }
        });
    }


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
    public Loader<List <Song>> onCreateLoader(int i, Bundle bundle) {
        return new MyLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader <List<Song>> listLoader, List <Song> name) {
        adapter.setSongs(name);
        adapter.notifyDataSetChanged();
        //unregisterReceiver(receiver);
    }

    @Override
    public void onLoaderReset(Loader <List <Song>> listLoader) {
        adapter.setSongs(new ArrayList<Song>());
        adapter.notifyDataSetChanged();
        //unregisterReceiver(receiver);
    }

    public void onNew(View view) {

    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("ok", -1);
            if (progress != -1) {
                getLoaderManager().restartLoader(1, null, MainActivity.this);
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "not work", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
