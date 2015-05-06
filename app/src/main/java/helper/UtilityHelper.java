package helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    public void displayGPSAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
        alertDialogBuilder.setMessage("You should enable GPS before starting your jog with Jogger.")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                _context.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.create().show();

    }
}
