package eagleapp.com.holidaynotify.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import eagleapp.com.holidaynotify.db.table.CountryTable;

/**
 * Created by Pete on 8.11.2015.
 */
public class DbHandler extends SQLiteOpenHelper {
    private static DbHandler mInstance = null;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "holidayNotifyDb";

    private Context mCxt;
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHandler getInstance(Context ctx){
        if(mInstance == null){
            mInstance = new DbHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CountryTable.ON_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CountryTable.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}
