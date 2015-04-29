package org.gudim.android.jogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import helper.UtilityHelper;
import jogger.database.DbHandler;
import maps.MapHelper;
import model.Session;

public class MapsActivity extends MyActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLngBounds latLngBounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);

        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View activityMapsView = getLayoutInflater().inflate(R.layout.activity_maps, null);
        frameLayout.addView(activityMapsView);

        UtilityHelper utilityHelper = new UtilityHelper(getApplicationContext());
        if (utilityHelper.isConnectedToInternet()) {
            setUpMapIfNeeded();
        } else {
            Toast.makeText(getApplicationContext(), "The map could not be opened because of no internet connection.", Toast.LENGTH_LONG).show();
        }


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //startService(new Intent(getBaseContext(), MapService.class));

    }

    //custom
    @Override
    public void onBackPressed() {

        //stopService(new Intent(getBaseContext(), MapService.class));
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilityHelper utilityHelper = new UtilityHelper(getApplicationContext());
        if (utilityHelper.isConnectedToInternet()) {
            setUpMapIfNeeded();
        } else {
            Toast.makeText(getApplicationContext(), "The map could not be opened because of no internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
             setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Context context = getApplicationContext();
        DbHandler dbHandler = new DbHandler(context);
        MapHelper mapHelper = new MapHelper(context);
        List<Session> sessions = dbHandler.getSessions();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Session session : sessions) {
            LatLng location = mapHelper.getLocationFromAddress(session.address);
            if (location != null) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(session.title).snippet("" + session.id));
                builder.include(marker.getPosition());
            }

        }

        latLngBounds = builder.build();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 150));
            }

        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent detailIntent = new Intent(getApplicationContext(), SessionDetailActivity.class);
                //saving marker id in snippet
                detailIntent.putExtra(SessionDetailFragment.ARG_ITEM_ID, Integer.parseInt(marker.getSnippet()));
                startActivity(detailIntent);
                //Dont run default behavior
                return true;
            }
        });
    }
}
