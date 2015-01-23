package ru.eugene.exam2.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Window;
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
import ru.eugene.exam2.items.Item1;
import ru.eugene.exam2.items.Item1Source;
import ru.eugene.exam2.db.ItemsProvider;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CONTEXT_MENU_EDIT = 10;
    private static final int CONTEXT_MENU_DELETE = 20;
    private SimpleCursorAdapter adapter;
    private Context context;
    private ListView list;
    private Toast toast;
    private int posOfSelectedEl;
    private String sortBy = Item1Source.COLUMN_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My app");
//        getActionBar().setDisplayShowTitleEnabled(false);


        String[] from = new String[]{Item1Source.COLUMN_NAME, Item1Source.COLUMN_DATE};
        final int[] to = new int[]{R.id.name, R.id.date};
        adapter = new SimpleCursorAdapter(this, R.layout.main_list_item, null, from, to, 0);
        context = this;

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (toast != null) toast.cancel();
                toast = Toast.makeText(context, "position: " + position, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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

        Log.e("LOG", "onCreate: " + sortBy);
        if (savedInstanceState != null) {
            sortBy = savedInstanceState.getString("sortBy", Item1Source.COLUMN_DATE);
        }


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
                onCreateDialog(null).show();
                break;
            case R.id.action_delete:
                getContentResolver().delete(ItemsProvider.CONTENT_URI_ITEM1,
                        Item1Source.COLUMN_ID + ">?", new String[]{"0"});
                break;
            case R.id.action_sort:
                if (sortBy.equals(Item1Source.COLUMN_DATE)) {
                    sortBy = Item1Source.COLUMN_NAME;
                } else {
                    sortBy = Item1Source.COLUMN_DATE;
                }
                getLoaderManager().restartLoader(0, null, this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurDate() {
        Long curMillis = System.currentTimeMillis();
        Date curDate = new Date(curMillis);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        formatter.setTimeZone(cal.getTimeZone());

        return formatter.format(curDate);
    }

    public Dialog onCreateDialog(final Integer position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.dialog_insert, null);
        final EditText name = (EditText) inflatedView.findViewById(R.id.name);
        builder.setView(inflatedView)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Item1 item1 = new Item1();
                        item1.setName(name.getText().toString());
                        item1.setDate(getCurDate());
                        if (position == null) {
                            new AsyncInsert(context, ItemsProvider.CONTENT_URI_ITEM1)
                                    .execute(item1.generateContentValues());
                        } else {
//                            context.getContentResolver().update(ItemsProvider.CONTENT_URI_ITEM1, item1.generateContentValues(),
//                                    Item1Source.COLUMN_ID + "=?", new String[]{Integer.toString(posToId.get(position))});
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ItemsProvider.CONTENT_URI_ITEM1, null, null, null, sortBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() == 0) {
            initDatabase();
        }
        adapter.swapCursor(data);
    }

    private void initDatabase() {
        Item1 item1 = new Item1();
        item1.setName("default value");
        new AsyncInsert(context, ItemsProvider.CONTENT_URI_ITEM1)
                .execute(item1.generateContentValues());
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
                            int id = temp.getInt(temp.getColumnIndex(Item1Source.COLUMN_ID));
                            context.getContentResolver().delete(ItemsProvider.CONTENT_URI_ITEM1, Item1Source.COLUMN_ID + "=?",
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
