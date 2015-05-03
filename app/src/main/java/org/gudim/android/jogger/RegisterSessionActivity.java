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

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import jogger.database.DbHandler;
import maps.MapService;
import model.Session;


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
        if (MapService.IsStarted) {
            registerSessionButton.setText("Stop");
            registerSessionButton.setBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            registerSessionButton.setText("Start");
            registerSessionButton.setBackgroundColor(Color.parseColor("#00FF55"));
        }

        registerSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapService.class);
                if (_mapService == null || !_mapService.IsStarted) {
                    startMapService();
                } else {
                    stopMapService();
                }

            }
        });
    }

    private void startMapService() {
        ((Button) findViewById(R.id.registerSessionButton)).setText("Stop");
        ((Button) findViewById(R.id.registerSessionButton)).setBackgroundColor(Color.parseColor("#FF0000"));

        Thread thread = new Thread() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MapService.class);
                if (!MapService.IsStarted)
                    startService(intent);
                getApplicationContext().bindService(intent, _serviceConnection, Context.BIND_AUTO_CREATE);
            }
        };
        thread.run();
    }

    private void stopMapService() {
        ArrayList<LatLng> locations = _mapService.locations;
        Date startTime = _mapService.startTime;
        Date endTime = new Date();
        if (locations != null && locations.size() > 0) {
            saveSession(locations, startTime, endTime);
            Toast.makeText(this, "Location count : " + locations.size(), Toast.LENGTH_SHORT).show();
        }
        ((Button) findViewById(R.id.registerSessionButton)).setText("Start");
        ((Button) findViewById(R.id.registerSessionButton)).setBackgroundColor(Color.parseColor("#00FF55"));
        getApplicationContext().unbindService(_serviceConnection);
        Intent intent = new Intent(getApplicationContext(), MapService.class);
        getApplicationContext().stopService(intent);
    }

    private void saveSession(ArrayList<LatLng> locations, Date startTime, Date endTime) {
        DbHandler dbHandler = new DbHandler(this);
        ///GET DISTANCE
        //dbHandler.insertSession(null);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (_mapService.IsStarted) {
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
        if (_mapService.IsStarted)
            getApplicationContext().getApplicationContext().unbindService(_serviceConnection);
    }

    ServiceConnection _serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            MapService.ServiceBinder serviceBinder = (MapService.ServiceBinder) binder;
            _mapService = serviceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            _mapService = null;
        }
    };
}
