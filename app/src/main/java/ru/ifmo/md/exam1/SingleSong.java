package ru.ifmo.md.exam1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class SingleSong extends ActionBarActivity {

    private String artist;
    private String name;
    private String genres;
    private String population;
    private String year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_song);
        artist = getIntent().getStringExtra("artist");
        name = getIntent().getStringExtra("name");
        genres = getIntent().getStringExtra("genres");
        population = getIntent().getStringExtra("popolarity");
        year = getIntent().getStringExtra("year");
        TextView art = (TextView) findViewById(R.id.artist);
        TextView n = (TextView) findViewById(R.id.name);
        TextView g = (TextView) findViewById(R.id.genres);
        TextView pop = (TextView) findViewById(R.id.popularity);
        TextView y = (TextView) findViewById(R.id.year);
        art.setText(artist);
        n.setText(name);
        g.setText(genres);
        pop.setText(population);
        y.setText(year);
    }

    public void onDelete(View view) {
        getContentResolver().delete(MyContentProvider.SONGS_CONTENT_URI, SQLiteHelper.ARTIST + "=? AND " + SQLiteHelper.NAME + "=? AND " + SQLiteHelper.POPULARITY + "=? AND " + SQLiteHelper.GENRES + "=? AND " + SQLiteHelper.YEAR + "=?", new String[]{artist, name, population, genres, year});
        onBackPressed();
        //Intent intent = new Intent(SingleSong.this, MainActivity.class);
        //startActivity(intent);
    }
}
