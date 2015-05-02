package org.gudim.android.jogger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import maps.MapService;


public class RegisterSessionActivity extends MyActionBarActivity {
    private MapService _mapService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View activityRegisterSessionDetailView = getLayoutInflater().inflate(R.layout.activity_register_session, null);
        frameLayout.addView(activityRegisterSessionDetailView);

        Button registerSessionButton = (Button) findViewById(R.id.registerSessionButton);
        registerSessionButton.setText("Start");
        registerSessionButton.setBackgroundColor(Color.parseColor("#00FF55"));

        registerSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button) findViewById(R.id.registerSessionButton)).setText("Stop");
                ((Button) findViewById(R.id.registerSessionButton)).setBackgroundColor(Color.parseColor("#FF0000"));

                Intent intent = new Intent(getApplicationContext(), MapService.class);
                if (_mapService == null || !_mapService.isStarted) {

                    Thread thread = new Thread() {
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MapService.class);
                            startService(intent);
                            getApplicationContext().bindService(intent, _serviceConnection, Context.BIND_AUTO_CREATE);
                        }
                    };
                    thread.run();
                } else {
                    ((Button) findViewById(R.id.registerSessionButton)).setText("Start");
                    ((Button) findViewById(R.id.registerSessionButton)).setBackgroundColor(Color.parseColor("#00FF55"));
                    getApplicationContext().unbindService(_serviceConnection);
                    getApplicationContext().stopService(intent);
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (_mapService != null && _mapService.isStarted) {
            Thread thread = new Thread() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MapService.class);
                    getApplicationContext().bindService(intent, _serviceConnection, Context.BIND_AUTO_CREATE);
                }
            };
            thread.run();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unbindService(_serviceConnection);
    }

    ServiceConnection _serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            MapService.ServiceBinder serviceBinder = (MapService.ServiceBinder) binder;
            _mapService = serviceBinder.getService();
            Toast.makeText(getApplicationContext(), "Service binding succeeded. Count: " + _mapService.counter, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            _mapService = null;
        }
    };
}
