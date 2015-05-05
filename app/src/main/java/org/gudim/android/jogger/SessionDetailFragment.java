package org.gudim.android.jogger;

import android.content.Context;
import android.content.Intent;
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


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.List;

import helper.UtilityHelper;
import jogger.database.DbHandler;
import maps.MapHelper;
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
        if (utilityHelper.isConnectedToInternet()) {
            setUpMapIfNeeded();
        } else {
            Toast.makeText(getActivity(), "The map could not be opened because of no internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpMapIfNeeded() {
        if (mMap == null) {
        mMap =  getMapFragment().getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
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

        Context context = getActivity();
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
}
