package org.gudim.android.jogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hansg_000 on 13.04.2015.
 */
public class DbHandler {
    private DbHelper dbHelper;
    private DateFormat dateFormat;

    public DbHandler(Context context) {
        dbHelper = new DbHelper(context);
        dateFormat = new SimpleDateFormat("dd//MM/yyyy");
    }

    public void closeDbHelper() {
        dbHelper.close();
    }

    public boolean insertSession(Session session) {
        SQLiteDatabase db;
        try {
            db = dbHelper.getWritableDatabase();
            String query = "INSERT INTO " + SessionContract.SessionEntry.TABLE_NAME + " ("
                    + SessionContract.SessionEntry.COLUMN_NAME_IMAGEURL + ", "
                    + SessionContract.SessionEntry.COLUMN_NAME_DATE + ", "
                    + SessionContract.SessionEntry.COLUMN_NAME_DURATION + ", "
                    + SessionContract.SessionEntry.COLUMN_NAME_LENGTH + ", "
                    + SessionContract.SessionEntry.COLUMN_NAME_TITLE
                    + ") VALUES ("
                    + session.imageUrl
                    + ","
                    + dateFormat.format(session.date)
                    + ","
                    + session.duration
                    + ","
                    + session.length
                    + ","
                    + session.title
                    + ")";
            db.execSQL(query);
        } catch (SQLiteException ex) {
            Log.e("Session insert failed.", "Writing to the database failed.");
            ex.printStackTrace();
            return false;
        }
        if (db != null) {
            db.close();
        }
        return true;
    }

    public List<Session> getSessions() {
        SQLiteDatabase db;
        List<Session> sessions = new ArrayList<Session>();
        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + SessionContract.SessionEntry.TABLE_NAME;

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Integer sessionId = cursor.getInt(cursor.getColumnIndex(SessionContract.SessionEntry._ID));
                Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_DATE)));
                String title = cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_TITLE));
                double length = cursor.getDouble(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_LENGTH));
                double duration = cursor.getDouble(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_DURATION));
                String imageUrl = cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_IMAGEURL));

                Session session = new Session(date, title, length, duration, imageUrl);
                session.id = sessionId;

                sessions.add(session);
                cursor.moveToNext();
            }
        } catch (SQLiteException ex) {
            Log.e("Session get failed.", "Reading from the database failed.");
        } catch (ParseException ex) {
            Log.e("Session get failed.", "Could not parse date.");
        }
        return sessions;
    }

    public Session getSession(int id) {
        SQLiteDatabase db;
        Session session = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + SessionContract.SessionEntry.TABLE_NAME;

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            if (cursor != null && cursor.moveToFirst()) {
                Integer sessionId = cursor.getInt(cursor.getColumnIndex(SessionContract.SessionEntry._ID));
                Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_DATE)));
                String title = cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_TITLE));
                double length = cursor.getDouble(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_LENGTH));
                double duration = cursor.getDouble(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_DURATION));
                String imageUrl = cursor.getString(cursor.getColumnIndex(SessionContract.SessionEntry.COLUMN_NAME_IMAGEURL));

                session = new Session(date, title, length, duration, imageUrl);
                session.id = sessionId;
            }
        } catch (SQLiteException ex) {
            Log.e("Session get failed.", "Reading from the database failed.");
        } catch (ParseException ex) {
            Log.e("Session get failed.", "Could not parse date.");
        }
        return session;
    }

}
