package ru.ifmo.md.exam1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class InitializerService extends IntentService {

    public InitializerService() {
        super("InitializerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }
}
