package org.gudim.android.jogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by hansg_000 on 20.03.2015.
 */
public class SessionsAdapter extends ArrayAdapter<Session> {
    public SessionsAdapter(Context context, ArrayList<Session> sessions) {
        super(context, 0, sessions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Session session = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_session, parent, false);
        }
        TextView textViewDate = (TextView) convertView.findViewById(R.id.sessionDate);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.sessionTitle);
        TextView textViewLength = (TextView) convertView.findViewById(R.id.sessionLength);
        TextView textViewDuration = (TextView) convertView.findViewById(R.id.sessionDuration);
        ImageView imageViewImage = (ImageView) convertView.findViewById(R.id.sessionImage);

        textViewDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(session.date));
        textViewTitle.setText(session.title);
        textViewLength.setText(String.format("Lengde: " + session.length + "km"));
        textViewDuration.setText(String.format("Varighet: " + session.duration + "minutter"));

        new DownloadImageTask(imageViewImage).execute(session.imageUrl.toString());
        /*try {
            InputStream stream = session.imageUrl.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            imageViewImage.setImageBitmap(bitmap);
            stream.close();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error: Could not load bitmap from URL for item: " + session.title + ". Errormsg: " + ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }*/

        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception ex) {
                Toast.makeText(getContext(), "Error: Could not load bitmap from URL.  Errormsg: " + ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


