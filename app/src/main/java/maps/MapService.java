package maps;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
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

import org.gudim.android.jogger.R;
import org.gudim.android.jogger.RegisterSessionActivity;

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
    private final int _notificationId = 1;
    public static boolean IsStarted = false;
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
        if (!IsStarted) {
            IsStarted = true;
            _locationRequest = LocationRequest.create();
            _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            _locationRequest.setInterval(5000);
            _googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            startTime = new Date();
            _googleApiClient.connect();
            startNotification();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (_googleApiClient.isConnected()) {
            _googleApiClient.disconnect();
        }
        stopNotification();
        IsStarted = false;
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        public MapService getService() {
            return MapService.this;
        }
    }

    //LOCATION

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(_googleApiClient, _locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        locations.add(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Location connection failed", Toast.LENGTH_SHORT).show();
    }


    public void startNotification() {
        Intent intent = new Intent(this, RegisterSessionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_stat_social_whatshot)
                        .setContentTitle("Jogger")
                        .setContentText("Jogger is tracking you!")
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(_notificationId, builder.build());
    }

    public void stopNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(_notificationId);
    }
}
