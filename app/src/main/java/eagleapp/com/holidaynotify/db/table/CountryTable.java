package eagleapp.com.holidaynotify.db.table;

import android.provider.BaseColumns;

/**
 * Created by Pete on 8.11.2015.
 */
public abstract class CountryTable implements BaseColumns {
    public static final String TABLE_NAME = "countries";
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    //columns
    public static final String FULL_NAME = "fullName";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String TO_DATE = "toDate";
    public static final String FROM_DATE = "fromDate";

    public static final String ON_CREATE =
            "CREATE TABLE " + TABLE_NAME +
            " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FULL_NAME + " TEXT," +
            COUNTRY_CODE + " TEXT NOT NULL UNIQUE," +
            TO_DATE + " TEXT," +
            FROM_DATE + " TEXT)";
}
