package ru.ya.exam2.activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.apache.http.cookie.MalformedCookieException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

import ru.ya.exam2.R;
import ru.ya.exam2.database.MContentProvider;
import ru.ya.exam2.database.MSQLiteHelper;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String SORT_ORDER = "SORT_ORDER";
    ListView listView;
    SimpleCursorAdapter adapter;
//    String sortOrder = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("----------------------", "!!");
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
        Log.e("before ", "if");
        if (cursor.getCount() == 0) {
            Log.e("in if:", "---------------");
            new AsyncTask<String , String , String >() {
                @Override
                protected String doInBackground(String... params) {
                    Log.e("start: ", "do in Backgroud");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.music)));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    String text;
                    try {
                        while ((line = reader.readLine()) != null)
                            stringBuilder.append(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    text = stringBuilder.toString();
                    try {
                        JSONArray jsonArray = new JSONArray(text);
                        Log.e("array: ", "" + jsonArray.length());
                        //ContentValues [] valuesList = new ContentValues[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("add item: ", "" + i);
                            JSONObject song = jsonArray.getJSONObject(i);
                            String name = song.getString("name");
                            String [] namePlusTitle = name.split("–");
                            //Log.e("length: ", " " + namePlusTitle.length);
                            if (namePlusTitle.length < 2) throw new Error();
                            String singer = namePlusTitle[0];
                            String title = namePlusTitle[1];
                            String url = song.getString("url");
                            String duration = song.getString("duration");
                            String year = song.getString("year");
                            JSONArray genresArray = song.getJSONArray("genres");
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < genresArray.length(); j++) {
                                String genre = genresArray.getString(j);
                                sb.append(genre);
                                //Log.e("one genre: ", genre);
                                sb.append("|");
                            }
                            String genres = sb.toString();
                            Log.e("genres: ", genres);
                            ContentValues values = new ContentValues();
                            values.put(MSQLiteHelper.COLUMN_SINGER, singer);
                            values.put(MSQLiteHelper.COLUMN_TITLE, title);
                            values.put(MSQLiteHelper.COLUMN_DURATION, duration);
                            values.put(MSQLiteHelper.COLUMN_GENRES, genres);
                            values.put(MSQLiteHelper.COLUMN_URL, url);
                            values.put(MSQLiteHelper.COLUMN_YEAR, year);
                            getContentResolver().insert(MContentProvider.URI_A, values);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("some thing bad: ", "with JSON");
                        throw new Error();
                    }
                    Log.e("ok: ", "!!!!!!!");
                    Cursor cursor1 = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
                    Log.e("cnt in database:", " " + cursor1);
                    return null;
                }
            }.execute();

        }
        else {
            Log.e("no big: ", "add: " + cursor.getCount());
        }


        listView = (ListView) findViewById(R.id.list_main);
        adapter = new SimpleCursorAdapter(this, R.layout.item_main, null, new String[]{MSQLiteHelper.COLUMN_LIST_TITLE, MSQLiteHelper.COLUMN_ID}, new int[]{R.id.main_item_text, R.id.textid}, 0);

        listView.setAdapter(adapter);

        initOnClickListener();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data_base) {
            clearDataBase();
        }
        if (id == R.id.add_item_main) {
            addItem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MContentProvider.URI_B, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void addItem() {
        Intent intent = new Intent(this, NewPlayListActivity.class);
        startActivity(intent);


        //View view = LayoutInflater.from(this).inflate(R.layout.add_entry, null);

//        new AlertDialog.Builder(this)
//                .setView(view)
//                .setMessage(getString(R.string.add_entry))
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ContentValues values = new ContentValues();
//                        values.put(MSQLiteHelper.COLUMN_TEXT, editText.getText().toString());
//                        values.put(MSQLiteHelper.COLUMN_DATE, getDate());
//                        getContentResolver().insert(MContentProvider.URI_A, values);
//                    }
//                })
//                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .show();
    }

    private void initOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("in on item ", "click");
                final String dataBaseId = ((TextView) view.findViewById(R.id.textid)).getText().toString();
                Intent intent = new Intent(MainActivity.this, PlayListActivity.class);
                intent.putExtra(MSQLiteHelper.COLUMN_ID, dataBaseId);
                startActivity(intent);
            }
        });

    }

    public String getDate() {
        Long id = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return format.format(new Date(id));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private void clearDataBase() {
        getContentResolver().delete(MContentProvider.URI_A, MSQLiteHelper.COLUMN_ID + ">0", null);
        getContentResolver().delete(MContentProvider.URI_B, MSQLiteHelper.COLUMN_ID + ">0", null);
        getContentResolver().delete(MContentProvider.URI_C, MSQLiteHelper.COLUMN_ID + ">0", null);
        Log.e("delete: ", "all");
    }
}




