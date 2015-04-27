package org.gudim.android.jogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;

import jogger.database.DbHandler;
import model.Session;

public class SessionDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Session selectedSession;

    public SessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            try {
                DbHandler dbHandler = new DbHandler(getActivity());
                selectedSession = dbHandler.getSession(getArguments().getInt(ARG_ITEM_ID));
            } catch (Exception ex) {
                Log.e("Error: onCreate", "Error while getting selected session from database");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session_detail, container, false);


        if (selectedSession != null) {
            TextView textViewTitle = (TextView) rootView.findViewById(R.id.sessionDetailTitle);
            TextView textViewDate = (TextView) rootView.findViewById(R.id.sessionDetailDate);
            TextView textViewLength = (TextView) rootView.findViewById(R.id.sessionDetailLength);
            TextView textViewDuration = (TextView) rootView.findViewById(R.id.sessionDetailDuration);

            textViewDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedSession.date));
            textViewTitle.setText(selectedSession.title);
            textViewLength.setText(String.format("Length: " + String.format("%.1f", selectedSession.length) + " km"));
            textViewDuration.setText(String.format("Duration: " + String.format("%.0f", selectedSession.duration) + " minutes"));
            getActivity().setTitle(selectedSession.title);
        }

        return rootView;
    }
}
