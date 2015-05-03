package helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hansg_000 on 28.04.2015.
 */
public class UtilityHelper {
    private Context _context;
    public UtilityHelper(Context context)
    {
_context = context;
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    public double getDateDifferenceInSeconds(Date startDate, Date endDate) {
        return (double)(endDate.getTime() - startDate.getTime()) / 1000;
    }
}
