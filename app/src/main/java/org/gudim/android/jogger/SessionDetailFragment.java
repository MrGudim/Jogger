package org.gudim.android.jogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.gudim.android.jogger.dummy.DummyContent;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * A fragment representing a single Session detail screen.
 * This fragment is either contained in a {@link SessionListActivity}
 * in two-pane mode (on tablets) or a {@link SessionDetailActivity}
 * on handsets.
 */
public class SessionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Session selectedSession;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            try{
                DbHandler dbHandler = new DbHandler(getActivity());
                selectedSession = dbHandler.getSession(getArguments().getInt(ARG_ITEM_ID));
            }
            catch(Exception ex)
            {
                Log.e("Error: onCreate", "Error while getting selected session from database");
            }
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (selectedSession != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            TextView textViewTitle = (TextView) rootView.findViewById(R.id.sessionDetailTitle);
            TextView textViewDate = (TextView) rootView.findViewById(R.id.sessionDetailDate);
            TextView textViewLength = (TextView) rootView.findViewById(R.id.sessionDetailLength);
            TextView textViewDuration = (TextView) rootView.findViewById(R.id.sessionDetailDuration);

            textViewDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedSession.date));
            textViewTitle.setText(selectedSession.title);
            textViewLength.setText(String.format("Length: " + String.format("%.1f",selectedSession.length) + " km"));
            textViewDuration.setText(String.format("Duration: " + String.format("%.0f",selectedSession.duration) + " minutes"));
        }

        return rootView;
    }
}
