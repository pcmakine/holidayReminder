package eagleapp.com.holidaynotify.db.table;

import android.provider.BaseColumns;

/**
 * Created by Pete on 9.11.2015.
 */
public class DayTable implements BaseColumns{
    public static final String TABLE_NAME = "days";

    //columns
    public static final String DATE = "holidayDate";
    public static final String LOCAL_NAME = "localName";
    public static final String ENGLISH_NAME = "englishName";
    public static final String NOTES = "notes";
    public static final String REGION_ID = "regionId";

    public static final String ON_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE + " TEXT NOT NULL," +
                    LOCAL_NAME + " TEXT," +
                    ENGLISH_NAME + " TEXT," +
                    NOTES + " TEXT," +
                    REGION_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + REGION_ID + ") REFERENCES " +
                    RegionTable.TABLE_NAME + "(" + RegionTable._ID + ")," +
                    " UNIQUE(" + DATE + ", " + LOCAL_NAME + ", " + REGION_ID + ") ON CONFLICT REPLACE)";
}
