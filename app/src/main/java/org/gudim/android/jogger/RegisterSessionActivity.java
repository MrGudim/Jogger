package org.gudim.android.jogger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
        registerSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mapService.counter++;
                Toast.makeText(getApplicationContext(), "Click. Count: " + _mapService.counter, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, MapService.class);
        if (_mapService == null || !_mapService.isStarted) {
            startService(intent);
        }
        bindService(intent, _serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(_serviceConnection);
    }

    ServiceConnection _serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            MapService.ServiceBinder serviceBinder = (MapService.ServiceBinder) binder;
            _mapService = serviceBinder.getService();
            Toast.makeText(getApplicationContext(), "Service binding succeeded. Count: " + _mapService.counter, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            _mapService = null;
        }
    };
}
