package eagleapp.com.holidaynotify.db.table;

import android.provider.BaseColumns;

/**
 * Created by Pete on 11.11.2015.
 */
public class RegionTable implements BaseColumns {
    public static final String TABLE_NAME = "regions";

    //columns
    public static final String NAME = "name";
    public static final String COUNTRY_CODE = "countryCode";

    public static final String ON_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME + " TEXT NOT NULL," +
                    COUNTRY_CODE + " TEXT NOT NULL," +
                    "FOREIGN KEY(" + COUNTRY_CODE + ") REFERENCES " +
                    CountryTable.TABLE_NAME + "(" + CountryTable.COUNTRY_CODE + ")," +
                    " UNIQUE(" + NAME + ", " + COUNTRY_CODE + ") ON CONFLICT REPLACE)";
}
