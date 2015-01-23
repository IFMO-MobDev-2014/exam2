package ru.eugene.exam2.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.eugene.exam2.R;
import ru.eugene.exam2.db.AsyncInsert;
import ru.eugene.exam2.db.ParseSongs;
import ru.eugene.exam2.db.SongsProvider;
import ru.eugene.exam2.items.PlayListsSource;
import ru.eugene.exam2.items.Song;
import ru.eugene.exam2.items.SongsSource;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CONTEXT_MENU_EDIT = 10;
    private static final int CONTEXT_MENU_DELETE = 20;
    private SimpleCursorAdapter adapter;
    private Context context;
    private ListView list;
    private Toast toast;
    private int posOfSelectedEl;
    private String sortBy = SongsSource.COLUMN_YEAR;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My app");

        String[] from = new String[]{PlayListsSource.COLUMN_NAME, PlayListsSource.COLUMN_DATE};
        final int[] to = new int[]{R.id.name, R.id.date};
        adapter = new SimpleCursorAdapter(this, R.layout.main_list_item, null, from, to, 0);
        context = this;

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ViewPlayList.class);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(list);
                posOfSelectedEl = position;
                openContextMenu(list);
                return true;
            }
        });

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

        switch (id) {
            case R.id.action_new:
                Intent intent = new Intent(this, AddPlayList.class);
                startActivity(intent);
                break;
            case R.id.action_delete:
                getContentResolver().delete(SongsProvider.CONTENT_URI_PLAY_LIST,
                        PlayListsSource.COLUMN_ID + ">?", new String[]{"0"});
                getContentResolver().delete(SongsProvider.CONTENT_URI_SONG,
                        SongsSource.COLUMN_ID + ">?", new String[]{"0"});
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, SongsProvider.CONTENT_URI_PLAY_LIST, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e("LOG", "load finish");
        if (data == null || data.getCount() == 0) {
            initDatabase();
        }
        adapter.swapCursor(data);
    }

    private void initDatabase() {
        Log.e("LOG", "init");
        Intent intent = new Intent(this, ParseSongs.class);
        startService(intent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sortBy", sortBy);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("What do you want to do?");
        menu.add(0, CONTEXT_MENU_EDIT, 0, "edit");
        menu.add(0, CONTEXT_MENU_DELETE, 0, "delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_MENU_DELETE) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete feed")
                    .setMessage("Are you sure you want to delete this feed?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Cursor temp = adapter.getCursor();
                            temp.moveToPosition(posOfSelectedEl);
                            int id = temp.getInt(temp.getColumnIndex(SongsSource.COLUMN_ID));
                            context.getContentResolver().delete(SongsProvider.CONTENT_URI_SONG, SongsSource.COLUMN_ID + "=?",
                                    new String[]{String.valueOf(id)});
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else { //edit
//            Intent changeItem = new Intent(this, ChangeItem.class);
//            changeItem.putExtra("target", "edit");
//            changeItem.putExtra("name", hDataBaseFeeds.getFeeds().get(posOfSelectedEl));
//            changeItem.putExtra("url", hDataBaseFeeds.getUrlFeeds().get(posOfSelectedEl));
//            startActivityForResult(changeItem, CHANGE_ITEM_EDIT);
        }
        return super.onContextItemSelected(item);
    }

    // System.currentTimeMillis()

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
