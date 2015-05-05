package jogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hansg_000 on 12.04.2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
public static final String DATABASE_NAME = "Session.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SessionContract.SessionEntry.TABLE_NAME + " (" +
                    SessionContract.SessionEntry._ID + " INTEGER PRIMARY KEY," +
                    SessionContract.SessionEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    SessionContract.SessionEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    SessionContract.SessionEntry.COLUMN_NAME_IMAGEURL + TEXT_TYPE + COMMA_SEP +
                    SessionContract.SessionEntry.COLUMN_NAME_LENGTH + REAL_TYPE + COMMA_SEP +
                    SessionContract.SessionEntry.COLUMN_NAME_DURATION + REAL_TYPE  + COMMA_SEP +
                    SessionContract.SessionEntry.COLUMN_NAME_POSITIONS + TEXT_TYPE +
    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SessionContract.SessionEntry.TABLE_NAME;

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
