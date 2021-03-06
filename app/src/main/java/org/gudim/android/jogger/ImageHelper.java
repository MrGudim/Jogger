package org.gudim.android.jogger;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hansg_000 on 14.04.2015.
 */
public class ImageHelper {
    Context context;
    public final String IMAGE_DIR = "images";

    public ImageHelper(Context context) {
        this.context = context;
    }

    public String saveImageToInternalStorage(Bitmap image) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        String fileName = new SimpleDateFormat("yyyyMMdd'.jpg'").format(new Date());

        try {
            File directory = contextWrapper.getDir(IMAGE_DIR, Context.MODE_PRIVATE);
            File imagePath = new File(directory, fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.close();
        } catch (Exception ex) {
            Log.e("Saving image failed.", "ImageHelper.saveImageToInternalStorage() failed.");
            ex.printStackTrace();
        }
        
        return fileName;
    }

    public Bitmap loadImageFromInternalStorage(String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        Bitmap image = null;

        try {
            File directory = contextWrapper.getDir(IMAGE_DIR, Context.MODE_PRIVATE);
            File file = new File(directory, fileName);

            image = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            Log.e("Loading image failed.", "Imagehelper.loadImageFromInternalStorage() failed.");
            e.printStackTrace();
        }

        return image;
    }
}
