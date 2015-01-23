package ru.eugene.exam2.items;

import android.content.ContentValues;

/**
 * Created by eugene on 1/21/15.
 */
public class Item1 {
    private int id;
    private String name;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues generateContentValues() {
        ContentValues value = new ContentValues();

        value.put(Item1Source.COLUMN_NAME, name);
        value.put(Item1Source.COLUMN_DATE, date);

        return value;
    }
}
