package maps;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.BaseImplementation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.d;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by hansg_000 on 23.04.2015.
 */
public class MapService extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final IBinder _binder = new ServiceBinder();
    public boolean isStarted = false;
    private LocationRequest _locationRequest;
    private GoogleApiClient _googleApiClient;
    public ArrayList<LatLng> locations;
    public Date startTime;

    @Override
    public void onCreate() {
        locations = new ArrayList<LatLng>();
    }

    @Override
    public IBinder onBind(Intent arg) {
        return _binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isStarted = true;
        _locationRequest = LocationRequest.create();
        _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        _locationRequest.setInterval(5000);
        _googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        startTime = new Date();


        _googleApiClient.connect();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if(_googleApiClient.isConnected())
        {
            _googleApiClient.disconnect();
        }

        isStarted = false;
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        public MapService getService() {
            return MapService.this;
        }
    }

    ////////////LOCATION

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(_googleApiClient, _locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Location connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        locations.add(new LatLng(location.getLatitude(), location.getLongitude()));
        Toast.makeText(this, "Location changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Location connection failed", Toast.LENGTH_SHORT).show();
    }
}
