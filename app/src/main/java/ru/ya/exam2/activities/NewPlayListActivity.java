package ru.ya.exam2.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.TreeSet;

import ru.ya.exam2.CheckBoxData;
import ru.ya.exam2.OneSong;
import ru.ya.exam2.R;
import ru.ya.exam2.database.MContentProvider;
import ru.ya.exam2.database.MSQLiteHelper;

public class NewPlayListActivity extends ActionBarActivity {
    ListView listView;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<CheckBoxData> checkBoxAdapter;
    CheckBoxData[] checkBoxDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_play_list);
        listView = (ListView) findViewById(R.id.play_list);
        initSpinner();
        initCheckBox();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_play_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.play_save) {
            buttonSave();
        }

        return super.onOptionsItemSelected(item);
    }

    void initSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        Cursor cursor = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
        int sz = cursor.getCount();
        TreeSet<String> years = new TreeSet<>();
        for (int i = 0; i < sz; i++) {
            cursor.moveToPosition(i);
            years.add(cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_YEAR)));
        }

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.play_spinner_text);
        for (String s : years)
            spinnerAdapter.add(s);
        spinner.setAdapter(spinnerAdapter);
    }

    void initCheckBox() {
        spinner = (Spinner) findViewById(R.id.spinner);
        Cursor cursor = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
        int sz = cursor.getCount();
        TreeSet<String> genresTree = new TreeSet<>();
        for (int i = 0; i < sz; i++) {
            cursor.moveToPosition(i);
            String genres = cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_GENRES));
            for (int j = 0; j < genres.length(); j++) {
                StringBuilder sb = new StringBuilder();
                for (; genres.charAt(j) != '|'; j++)
                    sb.append(genres.charAt(j));
                genresTree.add(sb.toString());
            }
        }

        checkBoxAdapter = new ArrayAdapter<CheckBoxData>(this, R.layout.checkbox) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View mView;
                if (convertView == null)
                    mView = LayoutInflater.from(NewPlayListActivity.this).inflate(R.layout.checkbox, parent, false);
                else mView = convertView;
                CheckBox checkBox = (CheckBox) mView.findViewById(R.id.checkbox_item);
                CheckBoxData data = getItem(position);
                checkBox.setText(data.getName());
                checkBox.setChecked(data.isFlag());
                checkBox.setTag(position);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CheckBox checkBox1 = (CheckBox) buttonView;
                        Integer pos = (Integer) checkBox1.getTag();
                        checkBoxDatas[pos].setFlag(isChecked);
                        checkBox1.setChecked(isChecked);
                    }
                });
                return mView;
            }
        };
        checkBoxDatas = new CheckBoxData[genresTree.size()];

        int pos = 0;
        for (String s : genresTree) {
            if (s == null) throw new Error();
            checkBoxDatas[pos] = new CheckBoxData(false, s);
            checkBoxAdapter.add(checkBoxDatas[pos]);
            pos++;
        }
        listView.setAdapter(checkBoxAdapter);
    }

    void buttonSave() {
        Cursor cursor = getContentResolver().query(MContentProvider.URI_A, null, null, null, null);
        ArrayList<OneSong> firstList = new ArrayList<>();
        int sz = cursor.getCount();
        for (int i = 0; i < sz; i++) {
            cursor.moveToPosition(i);
            firstList.add(new OneSong(
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_SINGER)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_YEAR)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_URL)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_DURATION)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_POPULATION)),
                    cursor.getString(cursor.getColumnIndex(MSQLiteHelper.COLUMN_GENRES))
            ));
        }
        Log.e("sz first array: ", "" + firstList.size());
        ArrayList<OneSong> secondList = new ArrayList<>();
        String singer = ((EditText)findViewById(R.id.play_edit_artist)).getText().toString();
        if (singer != null && singer.length() > 0) {
            Log.e("artist ", "enable:" + singer);
            for (OneSong x : firstList) {
                if (x.getSinger().equals(singer))
                    secondList.add(x);
            }
            firstList = secondList;
            secondList = new ArrayList<>();
        }


        String year = spinnerAdapter.getItem((int)spinner.getSelectedItemId());
        //Log.e("year: ", "" + year);
        if (true) {
            for (OneSong x : firstList) {
                //Log.e("x: ", x.getYear());
                if (x.getYear().equals(year))
                    secondList.add(x);
            }
            firstList = secondList;
            secondList = new ArrayList<>();
        }

        Log.e("sz first array year: ", "" + firstList.size());

        for (OneSong x: firstList)  {
            boolean flag = false;
            String genres = x.getGenres();

            for (int i = 0; i < genres.length(); i++) {
                StringBuilder sb = new StringBuilder();
                for (; genres.charAt(i) != '|'; i++)
                    sb.append(genres.charAt(i));
                String genre = sb.toString();
                for (CheckBoxData y: checkBoxDatas) {
                    Log.e("compare: ", genre + "   " + y.getName());
                    if (y.getName().equals(genre) && y.isFlag())
                        flag = true;
                }
            }
            //Log.e("flag: ", "" + flag);
            if (flag) {
                //Log.e("before add: ", " " + secondList.size());
                secondList.add(x);
                //Log.e("before after: ", " " + secondList.size());
            }
            //Log.e("sz second: ", " " + secondList.size());
        }
        firstList = secondList;
        //secondList = new ArrayList<>();


        Log.e("sz first array: ", "" + firstList.size());

        ContentValues values = new ContentValues();
        values.put(MSQLiteHelper.COLUMN_LIST_TITLE, ((EditText) (findViewById(R.id.play_edit_name))).getText().toString());
        StringBuilder sb = new StringBuilder();
        for (OneSong x: firstList) {
            sb.append(x.getId());
            sb.append("|");
        }
        values.put(MSQLiteHelper.COLUMN_SONGS_ID, sb.toString());
        Log.e("x: ", sb.toString());
        Log.e("id: ", getContentResolver().insert(MContentProvider.URI_B, values).toString());
        //Log.e("sz first array: ", "" + firstList.size());
    }
}

