package org.gudim.android.jogger;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //custom
        ArrayList<Session> sessions = new ArrayList<Session>();
        try {
            sessions.add(new Session(new Date(), "Tittel1", 100.00, 200.00, new URL("http://cdn.superbwallpapers.com/wallpapers/animals/kitten-16219-400x250.jpg")));
            sessions.add(new Session(new Date(), "Tittel2", 110.00, 210.00, new URL("http://cdn.superbwallpapers.com/wallpapers/animals/kitten-16219-400x250.jpg")));
            sessions.add(new Session(new Date(), "Tittel3", 120.00, 220.00, new URL("http://cdn.superbwallpapers.com/wallpapers/animals/kitten-16219-400x250.jpg")));
            sessions.add(new Session(new Date(), "Tittel4", 130.00, 230.00, new URL("http://cdn.superbwallpapers.com/wallpapers/animals/kitten-16219-400x250.jpg")));
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Error: Malformed image URLS", Toast.LENGTH_SHORT).show();
        }
        SessionsAdapter sessionsAdapter = new SessionsAdapter(this,sessions);

        final ListView listView = (ListView) findViewById(R.id.ListViewSessions);
        listView.setAdapter(sessionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textViewTitle = (TextView) view.findViewById(R.id.sessionTitle);
                Toast.makeText(getApplicationContext(), "You clicked:  " + textViewTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
