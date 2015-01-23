package ru.ifmo.md.exam2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ru.ifmo.md.exam2.R;

public class DetailsActivity extends ActionBarActivity {
    public static final String EXTRA_AUTHOR     = "extra_author";
    public static final String EXTRA_TITLE      = "extra_title";
    public static final String EXTRA_URL        = "extra_url";
    public static final String EXTRA_DURATION   = "extra_duration";
    public static final String EXTRA_POPULARITY = "extra_popularity";
    public static final String EXTRA_GENRES     = "extra_genres";
    public static final String EXTRA_YEAR       = "extra_year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String author = intent.getStringExtra(EXTRA_AUTHOR);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String url = intent.getStringExtra(EXTRA_URL);
        String duration = intent.getStringExtra(EXTRA_DURATION);
        String popularity = intent.getStringExtra(EXTRA_POPULARITY);
        String genres = intent.getStringExtra(EXTRA_GENRES);
        String year = intent.getStringExtra(EXTRA_YEAR);

        TextView textauthor = (TextView) findViewById(R.id.det_author);
        TextView texttitle = (TextView) findViewById(R.id.det_title);
        TextView texturl = (TextView) findViewById(R.id.det_url);
        TextView textduration = (TextView) findViewById(R.id.det_duration);
        TextView textpopularity = (TextView) findViewById(R.id.det_popularity);
        TextView textgenres = (TextView) findViewById(R.id.det_genres);
        TextView textyear = (TextView) findViewById(R.id.det_year);

        textauthor.setText(author);
        texttitle.setText(title);
        texturl.setText(url);
        textduration.setText(duration);
        textpopularity.setText(popularity);
        textgenres.setText(genres);
        textyear.setText(year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
