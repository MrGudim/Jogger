package org.gudim.android.jogger;

import android.provider.BaseColumns;

/**
 * Created by hansg_000 on 12.04.2015.
 */
public final class SessionContract {
    public SessionContract()
    {

    }

    public static abstract class SessionEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "session";
        public static final String COLUMN_NAME_TITLE = "sessiontitle";
        public static final String COLUMN_NAME_DATE = "sessiondate";
        public static final String COLUMN_NAME_LENGTH = "sessionlength";
        public static final String COLUMN_NAME_DURATION = "sessionduration";
        public static final String COLUMN_NAME_IMAGEURL = "sessionimmage";
    }
}
