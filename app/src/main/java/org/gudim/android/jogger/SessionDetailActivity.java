package org.gudim.android.jogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;


public class SessionDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, SessionListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
