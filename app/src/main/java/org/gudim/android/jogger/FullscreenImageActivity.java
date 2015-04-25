package org.gudim.android.jogger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class FullscreenImageActivity extends MyActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fullscreen_image);
        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View fullscreenImageActivityView = getLayoutInflater().inflate(R.layout.activity_fullscreen_image, null);
        frameLayout.addView(fullscreenImageActivityView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        ImageView imageView = (ImageView)findViewById(R.id.fullscreenImage);
        imageView.setImageBitmap(bitmap);
    }
}
