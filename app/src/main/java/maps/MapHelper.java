package maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

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
}
