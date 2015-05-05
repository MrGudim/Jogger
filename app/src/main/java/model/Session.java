package model;

import android.media.Image;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hansg_000 on 20.03.2015.
 */
public class Session {
    public Session(Date date, String title, double length, double duration, String imageUrl, ArrayList<LatLng> positions)
    {
        this.date = date;
        this.title = title;
        this.length = length;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.positions = positions;
    }
    public Date date;
    public String title;
    public double length;
    public double duration;
    public String imageUrl;
    public ArrayList<LatLng> positions;
    public int id;
}
