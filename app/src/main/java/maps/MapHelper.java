package maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Created by hansg_000 on 27.04.2015.
 */
public class MapHelper {
    Geocoder geocoder;

    public MapHelper(Context context) {
        this.geocoder = new Geocoder(context);
    }

    public LatLng getLocationFromAddress(String address) {

        if (address != null && !address.isEmpty()) {
            try {
                List<Address> addressList = geocoder.getFromLocationName(address, 1);
                if (addressList != null && addressList.size() > 0) {
                    return new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String getAddressFromLocation(LatLng location) {
        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if(!addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public Double getTotalDistanceBetweenMultiplePoints(ArrayList<LatLng> points)
    {
        Double distance = 0.00;
        LatLng previousPoint = null;
        for(LatLng point : points)
        {
            if(previousPoint != null)
            {
                distance += getDistanceBetweenPoints(previousPoint, point);
            }
            previousPoint = point;

        }
        return distance;
    }

    //Using the Haversine formula to get the distance between two points
    public Double getDistanceBetweenPoints(LatLng start, LatLng end)
    {
        int earthRadius = 6371000;
        Double startLatRadians = getRadiansFromDegrees(start.latitude);
        Double endLatRadians = getRadiansFromDegrees(end.latitude);

        Double latDifference = getRadiansFromDegrees(end.latitude - start.latitude);
        Double lngDifference = getRadiansFromDegrees(end.longitude - start.longitude);

        Double a = Math.sin(latDifference/2) * Math.sin(latDifference/2) + Math.cos(startLatRadians) * Math.cos(endLatRadians) * Math.sin(lngDifference/2) * Math.sin(lngDifference/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = earthRadius * c;

        return distance;
    }
    public Double getRadiansFromDegrees(double degrees)
    {
        return degrees * (Math.PI/180);
    }
}
