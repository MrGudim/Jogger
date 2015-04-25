package org.gudim.android.jogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by hansg_000 on 25.04.2015.
 */

    public class GridViewGalleryAdapter extends ArrayAdapter<Bitmap> {
        public GridViewGalleryAdapter(Context context, ArrayList<Bitmap> images) {
            super(context, 0, images);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Bitmap image = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.gallery_grid_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.galleryListItemImageView);
            imageView.setImageBitmap(image);

            return convertView;
        }

    }
