package ru.eugene.exam2.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by eugene on 1/21/15.
 */
public class AsyncInsert extends AsyncTask<ContentValues, Void, Void> {
    private ContentResolver resolver;
    private Uri uri;
    private ContentValues values;

    public AsyncInsert(Context context, Uri uri) {
        this.resolver = context.getContentResolver();
        this.uri = uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        if (params.length > 1) {
            resolver.bulkInsert(uri, params);
        } else {
            resolver.insert(uri, params[0]);
        }
        return null;
    }
}
