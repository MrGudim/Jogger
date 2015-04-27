package model;

import android.media.Image;
import android.net.Uri;

import java.net.URL;
import java.util.Date;

/**
 * Created by hansg_000 on 20.03.2015.
 */
public class Session {
    public Session(Date date, String title, double length, double duration, String imageUrl, String address)
    {
        this.date = date;
        this.title = title;
        this.length = length;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.address = address;
    }
    public Date date;
    public String title;
    public double length;
    public double duration;
    public String imageUrl;
    public String address;
    public int id;
}
