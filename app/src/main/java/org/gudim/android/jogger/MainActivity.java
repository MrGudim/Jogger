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

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //custom
        ArrayList<Session> sessions = new ArrayList<Session>();


        final ListView listView = (ListView) findViewById(R.id.ListViewSessions);



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

    /*public void SendMessage(View v)
    {
        EditText editText = (EditText) findViewById(R.id.EditTextMessage);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_DIAL);
        sendIntent.setData(Uri.parse("tel:"+ editText.getText()));

// Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }*/

    public void onItemClick(View v)
    {

    }
}
