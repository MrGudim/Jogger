package org.gudim.android.jogger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import helper.GridViewGalleryAdapter;
import helper.ImageHelper;


public class GalleryActivity extends MyActionBarActivity {
    static final int STATUS_CODE_REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //injecting the layout into the content_frame of the menu layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        View activityGalleryView = getLayoutInflater().inflate(R.layout.activity_gallery, null);
        frameLayout.addView(activityGalleryView);

        Button button = (Button) findViewById(R.id.buttonTakePicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        GridView gridView = (GridView) findViewById(R.id.gridViewGalleryPictures);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Bitmap image = (Bitmap) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), FullscreenImageActivity.class);
                intent.putExtra("BitmapImage", image);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        populateImagegrid();
    }

    private void populateImagegrid()
    {
        GridView gridView = (GridView) findViewById(R.id.gridViewGalleryPictures);
        ImageHelper imageHelper = new ImageHelper(getApplicationContext());

        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        images.addAll(imageHelper.loadImagesFromInternalStorage());

        gridView.setAdapter(new GridViewGalleryAdapter(getApplicationContext(), images));
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


                startActivityForResult(takePictureIntent, STATUS_CODE_REQUEST_IMAGE_CAPTURE);



        }
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STATUS_CODE_REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageHelper imageHelper = new ImageHelper(getApplicationContext());
            imageHelper.saveImageToInternalStorage(imageBitmap);
            populateImagegrid();
        }
    }
}
