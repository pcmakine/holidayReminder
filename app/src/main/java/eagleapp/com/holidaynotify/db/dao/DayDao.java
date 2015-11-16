package eagleapp.com.holidaynotify.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.CountryTable;
import eagleapp.com.holidaynotify.db.table.DayTable;
import eagleapp.com.holidaynotify.db.table.RegionTable;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.domain.Region;
import eagleapp.com.holidaynotify.utils.CommonConversion;
import eagleapp.com.holidaynotify.utils.DateUtils;

/**
 * Created by Pete on 5.11.2015.
 */
public class DayDao extends Dao<Day>{
    public static final String TAG = CountryDao.class.getName();
    private static DayDao instance = new DayDao();
    private static final String QUERY_BY_COUNTRY = "SELECT * FROM " + DayTable.TABLE_NAME +
            " INNER JOIN " + RegionTable.TABLE_NAME + " ON " + DayTable.TABLE_NAME +
            "." + DayTable.REGION_ID + " = " + RegionTable.TABLE_NAME + "." + RegionTable._ID +
            " INNER JOIN " + CountryTable.TABLE_NAME + " ON " + RegionTable.TABLE_NAME +
            "." + RegionTable.COUNTRY_CODE + " = " + CountryTable.TABLE_NAME + "." + CountryTable.COUNTRY_CODE +
            " WHERE " + CountryTable.TABLE_NAME + "." + CountryTable.COUNTRY_CODE  + " = ?";

    private static final String QUERY_BY_COUNTRY_AND_ACTIVE_NOTIFICATION = "SELECT * FROM " + DayTable.TABLE_NAME +
            " INNER JOIN " + RegionTable.TABLE_NAME + " ON " + DayTable.TABLE_NAME +
            "." + DayTable.REGION_ID + " = " + RegionTable.TABLE_NAME + "." + RegionTable._ID +
            " INNER JOIN " + CountryTable.TABLE_NAME + " ON " + RegionTable.TABLE_NAME +
            "." + RegionTable.COUNTRY_CODE + " = " + CountryTable.TABLE_NAME + "." + CountryTable.COUNTRY_CODE +
            " WHERE " + CountryTable.TABLE_NAME + "." + CountryTable.COUNTRY_CODE  + " = ?" +
            " AND " + DayTable.TABLE_NAME + "."  + DayTable.NOTIFICATION_ACTIVE + " =?"
            ;
    private static final String QUERY_FIRST_ROW = "SELECT * FROM " + DayTable.TABLE_NAME +
            " ORDER BY " + DayTable.DATE + " ASC LIMIT 1";

    private DayDao(){
        this.tableName = DayTable.TABLE_NAME;
    }

    public static synchronized DayDao getInstance(){
        return instance;
    }

    public String insertOne(Context context, Day day){
        String errors = "";
        Long regionId = null;
        //shouldn't be necessary, the region that day has should always have an id
       /* if( day.getRegion().getId() == null ){
            regionId = RegionDao.getInstance().insertOne(context, day.getRegion());
            day.getRegion().setId(regionId);
        }*/
        SQLiteDatabase db = DbHandler.getInstance(context).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(DayTable.DATE, DateUtils.dateToDbString(day.getDate()));
        vals.put(DayTable.LOCAL_NAME, day.getLocalName());
        vals.put(DayTable.ENGLISH_NAME, day.getEnglishName());
        vals.put(DayTable.NOTES, day.getNotes());
        vals.put(DayTable.REGION_ID, day.getRegion().getId());
        vals.put(DayTable.NOTIFICATION_ACTIVE, CommonConversion.booleanToInt(day.isNotificationActive()));
        db.insert(DayTable.TABLE_NAME, null, vals);
        db.close();
        return errors;
    }

    public String insertMany(Context context, List<Day> days){
        String errors = "";
        for(Day day: days){
            insertOne(context, day);
        }
        return errors;
    }

    public Day loadFirst(Context context){
        Day day = null;
        SQLiteDatabase db = DbHandler.getInstance(context).getReadableDatabase();
        Cursor c = db.rawQuery(QUERY_FIRST_ROW, null);
        if(c != null) {
            c.moveToFirst();
            day = cursorToDomainObject(c);
            c.close();
        }
        db.close();
        return day;
    }

    public Day loadById(Long id){
        return executeQuery(null, DayTable._ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
    }

    public List<Day> loadByCountry(Context context, String countryCode){
        return executeRawQuery(QUERY_BY_COUNTRY, new String[] {countryCode});
    }

    //notification status: true = notification on, false = notification = off
    public List<Day> loadByCountryAndNotificationStatus(String countryCode, boolean notificationStatus){
        int notificationStatusAsInt = CommonConversion.booleanToInt(notificationStatus);
        List<Day> days = executeRawQuery(QUERY_BY_COUNTRY_AND_ACTIVE_NOTIFICATION, new String[] {countryCode, String.valueOf(notificationStatusAsInt)});
        Log.d(TAG, "Days in country " + countryCode + " with notification status " + notificationStatus + ": " + days.toString());
        return days;
    }

    @Override
    protected Day cursorToDomainObject(Cursor c) {
        Long id = c.getLong(c.getColumnIndex(DayTable._ID));
        Date date = DateUtils.stringToDate(c.getString(c.getColumnIndex(DayTable.DATE)));
        String localName = c.getString(c.getColumnIndex(DayTable.LOCAL_NAME));
        String englishName = c.getString(c.getColumnIndex(DayTable.ENGLISH_NAME));
        String notes = c.getString(c.getColumnIndex(DayTable.NOTES));
        Long regionId = c.getLong(c.getColumnIndex(DayTable.REGION_ID));
        Region region = RegionDao.getInstance().loadById(regionId);
        boolean notificationActive = CommonConversion.intToBoolean(c.getInt(c.getColumnIndex(DayTable.NOTIFICATION_ACTIVE)));
        return new Day(id, date, localName, englishName, notes, region, notificationActive);
    }
}
