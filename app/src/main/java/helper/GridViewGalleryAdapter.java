package helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import org.gudim.android.jogger.R;

import java.util.ArrayList;

/**
 * Created by hansg_000 on 25.04.2015.
 */

    public class GridViewGalleryAdapter extends ArrayAdapter<Bitmap> {

       ArrayList<Bitmap> mImages;
        public GridViewGalleryAdapter(Context context, ArrayList<Bitmap> images) {
            super(context, 0, images);
            mImages = images;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Bitmap image = super.getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.gallery_grid_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.galleryListItemImageView);
            imageView.setImageBitmap(image);

            return convertView;
        }

    @Override
    public Bitmap getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    }