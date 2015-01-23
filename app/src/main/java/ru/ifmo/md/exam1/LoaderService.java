package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class LoaderService extends IntentService {
    public static String PLAYLIST_ACTION = "playlist action";

    public LoaderService() {
        super("LoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
        }
    }
}
