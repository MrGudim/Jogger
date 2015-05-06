package org.gudim.android.jogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.List;

import helper.UtilityHelper;
import jogger.database.DbHandler;
import maps.MapHelper;
import maps.MapService;
import model.Session;

public class SessionDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Session _selectedSession;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLngBounds latLngBounds;
    private SupportMapFragment supportMapFragment;

    public SessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            try {
                DbHandler dbHandler = new DbHandler(getActivity());
                _selectedSession = dbHandler.getSession(getArguments().getInt(ARG_ITEM_ID));
            } catch (Exception ex) {
                Log.e("Error: onCreate", "Error while getting selected session from database");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        UtilityHelper utilityHelper = new UtilityHelper(getActivity());
        if (!utilityHelper.isConnectedToInternet()) {
            Toast.makeText(getActivity(), "Internet connection is needed for the full experience.", Toast.LENGTH_LONG).show();
        }
        setUpMapIfNeeded();
    }

    public void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = getMapFragment().getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Thread thread = new Thread() {
                    public void run() {
                        setUpMap();
                    }
                };
                thread.run();

            }
        }
        if (mMap != null) {
            UtilityHelper utilityHelper = new UtilityHelper(getActivity());
            if (utilityHelper.isConnectedToInternet()) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        }
    }

    private SupportMapFragment getMapFragment() {
        FragmentManager fm = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fm = getFragmentManager();
        } else {
            fm = getChildFragmentManager();
        }

        return (SupportMapFragment) fm.findFragmentById(R.id.detailMap);
    }

    public void setUpMap() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (_selectedSession.positions.size() >= 1) {
            for (int i = 0; i < _selectedSession.positions.size(); i++) {
                if (i == 0) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(_selectedSession.positions.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Start"));
                }
                if (i > 0) {
                    Polyline polyline = mMap.addPolyline(new PolylineOptions()
                            .add(_selectedSession.positions.get(i - 1), _selectedSession.positions.get(i))
                            .width(5)
                            .color(Color.RED));
                }
                if (i == _selectedSession.positions.size() - 1) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(_selectedSession.positions.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("End"));
                }
                builder.include(_selectedSession.positions.get(i));
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });

            latLngBounds = builder.build();

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 150));
                }

            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session_detail, container, false);

        if (_selectedSession != null) {
            TextView textViewTitle = (TextView) rootView.findViewById(R.id.sessionDetailTitle);
            TextView textViewDate = (TextView) rootView.findViewById(R.id.sessionDetailDate);
            TextView textViewLength = (TextView) rootView.findViewById(R.id.sessionDetailLength);
            TextView textViewDuration = (TextView) rootView.findViewById(R.id.sessionDetailDuration);

            textViewDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(_selectedSession.date));
            textViewTitle.setText(_selectedSession.title);
            textViewLength.setText(String.format("Length: " + String.format("%.1f", _selectedSession.length) + " km"));
            textViewDuration.setText(String.format("Duration: " + String.format("%02d:%02d:%02d", (int) _selectedSession.duration / 3600, (int) (_selectedSession.duration % 3600) / 60, (int) _selectedSession.duration % 60)));
            getActivity().setTitle(_selectedSession.title);
        }

        return rootView;
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if(mMap != null)
        {
            mMap = null;
        }
    }
}
