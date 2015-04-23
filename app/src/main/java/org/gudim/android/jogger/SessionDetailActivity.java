package org.gudim.android.jogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


public class SessionDetailActivity extends MyActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_session_detail);
        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View activitySessionDetailView = getLayoutInflater().inflate(R.layout.activity_session_detail, null);
        frameLayout.addView(activitySessionDetailView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putInt(SessionDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(SessionDetailFragment.ARG_ITEM_ID, 0));
            SessionDetailFragment fragment = new SessionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.session_detail_container, fragment)
                    .commit();
        }
    }
}
