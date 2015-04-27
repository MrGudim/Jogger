package helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.gudim.android.jogger.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Session;

/**
 * Created by hansg_000 on 20.03.2015.
 */
public class SessionsAdapter extends ArrayAdapter<Session> {
    public SessionsAdapter(Context context, ArrayList<Session> sessions) {
        super(context, 0, sessions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Session session = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_session, parent, false);
        }
        TextView textViewDate = (TextView) convertView.findViewById(R.id.sessionDate);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.sessionTitle);

        textViewDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(session.date));
        textViewTitle.setText(session.title);

        return convertView;
    }

}


