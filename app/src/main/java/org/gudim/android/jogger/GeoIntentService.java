package org.gudim.android.jogger;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


public class GeoIntentService extends IntentService {
    public GeoIntentService()
    {
        super("GeoIntentService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        Handler handler = new Handler();
        handler.post( new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Service started INTERNAL", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
