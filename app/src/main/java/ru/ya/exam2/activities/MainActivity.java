package ru.ya.exam2.activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
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
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

import ru.ya.exam2.R;
import ru.ya.exam2.database.MContentProvider;
import ru.ya.exam2.database.MSQLiteHelper;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    SimpleCursorAdapter adapter;
    String ordredBy = null;
    boolean flagOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_main);
        adapter = new SimpleCursorAdapter(this, R.layout.item_main, null,
                new String[]{MSQLiteHelper.COLUMN_TEXT, MSQLiteHelper.COLUMN_ID, MSQLiteHelper.COLUMN_DATE},
                new int[]{R.id.main_item_text, R.id.textid, R.id.main_item_date}, 0);
        listView.setAdapter(adapter);
        initOnClickListener();
        ordredBy = MSQLiteHelper.COLUMN_TEXT;
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
            getContentResolver().delete(MContentProvider.URI_A, MSQLiteHelper.COLUMN_ID + ">0", null);
            getContentResolver().delete(MContentProvider.URI_B, MSQLiteHelper.COLUMN_ID + ">0", null);
            getContentResolver().delete(MContentProvider.URI_C, MSQLiteHelper.COLUMN_ID + ">0", null);
        }
        if (id == R.id.add_item_main) {
            addItem();
        }
        if (id == R.id.change_sort_order) {
            flagOrder = !flagOrder;
            if (flagOrder) ordredBy = MSQLiteHelper.COLUMN_DATE;
            else ordredBy = MSQLiteHelper.COLUMN_TEXT;

            getLoaderManager().restartLoader(0, null, this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MContentProvider.URI_A, null, null, null, ordredBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void addItem() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_entry, null);
        final EditText editText = (EditText) view.findViewById(R.id.edit_add_entry);
        Log.e("finish add: ", "a");
        new AlertDialog.Builder(this)
                .setView(view)
                .setMessage(getString(R.string.add_entry))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put(MSQLiteHelper.COLUMN_TEXT, editText.getText().toString());
                        values.put(MSQLiteHelper.COLUMN_DATE, getDate());
                        ///Log.e("add to database: ", "---------");
                        getContentResolver().insert(MContentProvider.URI_A, values);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void initOnClickListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String dataBaseId = ((TextView) findViewById(R.id.textid)).getText().toString();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(getString(R.string.delete_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getContentResolver().delete(MContentProvider.URI_A, MSQLiteHelper.COLUMN_ID + "=?", new String[]{dataBaseId});
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public String getDate() {
        Long id = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return format.format(new Date(id));
    }

}




