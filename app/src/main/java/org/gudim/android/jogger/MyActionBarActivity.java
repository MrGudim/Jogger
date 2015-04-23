package org.gudim.android.jogger;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyActionBarActivity extends ActionBarActivity {
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        getSupportActionBar().setTitle("Jogger");
        initializeDrawer();
    }

    private void initializeDrawer() {
        ArrayList<String> drawerItems = new ArrayList<String>();
        drawerItems.add("New session");
        drawerItems.add("Sessions");
        drawerItems.add("Map");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, drawerItems));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                onItemSelected(position);
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_opened) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("Closed state");
                getSupportActionBar().show();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                //getSupportActionBar().setTitle("Open state");
                getSupportActionBar().hide();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void onItemSelected(int position) {
        // Specify the activity to show based on position
// 1 == Register, 2 == List, 3 == Map
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);

        //Register
        if (position == 0) {
            Intent intent = new Intent(this, SessionListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            if(this.getClass().toString() == SessionListActivity.class.toString())
                finish();
        }
        //List

        else if (position == 1) {
            Intent intent = new Intent(this, SessionListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
        //Map
        else if (position == 2) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.homeButton:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
            //This is the "up"/"back" button on the actionbar for slave activities
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //If you declare the onCreateOptionMenu method, which is the one where you put the elements in the actionbar,
    //in you main activity (A), all the other activities that extend A without re-declaring that method will have the same actionbar of A.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
