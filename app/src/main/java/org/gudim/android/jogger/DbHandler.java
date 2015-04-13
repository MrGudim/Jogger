package org.gudim.android.jogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

/**
 * Created by hansg_000 on 13.04.2015.
 */
public class DbHandler {
    private DbHelper dbHelper;

    public DbHandler(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void closeDbHelper()
    {
        dbHelper.close();
    }

    public boolean insertSession(Session session)
    {
        SQLiteDatabase db;
        try {
            db = dbHelper.getWritableDatabase();
            String query = "INSERT INTO " + CarFeedEntry.TABLE_NAME + " ("
                    + CarFeedEntry.COLUMN_CAR_BRAND + ", "
                    + CarFeedEntry.COLUMN_CAR_MODEL + ", "
                    + CarFeedEntry.COLUMN_YEAR + ", "
                    + CarFeedEntry.COLUMN_ODOMETER + ", "
                    + CarFeedEntry.COLUMN_FUELTANK + ") VALUES (" + dbCarBrand
                    + "," + dbCarModel + "," + dbYear + ","
                    + dbOdometer + ","
                    + dbFueltank + ")";
            db.execSQL(query);
        } catch (SQLiteException ex) {
            Log.e("Error in: insertCar method in CarDBHandler class",
                    "Could not open database for writing.");
            ex.printStackTrace();
            return false;
        }
        db.close();
    }

    public List<Session> getSessions()
    {
        return null;
    }

    public Session getSession(int id)
    {
        return null;
    }

}
