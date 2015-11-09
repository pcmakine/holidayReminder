package eagleapp.com.holidaynotify.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.CountryTable;
import eagleapp.com.holidaynotify.db.table.DayTable;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.utils.DateUtils;

/**
 * Created by Pete on 5.11.2015.
 */
public class DayDao {
    public static final String TAG = CountryDao.class.getName();
    private static DayDao instance = new DayDao();

    private DayDao(){
    }

    public static synchronized DayDao getInstance(){
        return instance;
    }

    public String insertOne(Context context, Day day){
        String errors = "";
        SQLiteDatabase db = DbHandler.getInstance(context).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(DayTable.DATE, DateUtils.dateToString(day.getDate()));
        vals.put(DayTable.LOCAL_NAME, day.getLocalName());
        vals.put(DayTable.ENGLISH_NAME, day.getEnglishName());
        vals.put(DayTable.NOTES, day.getNotes());
        vals.put(DayTable.COUNTRY_CODE, day.getCountryCode());
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

    public List<Day> loadByCountry(Context context, String countryCode){
        List<Day> days = new ArrayList<>();
        SQLiteDatabase db = DbHandler.getInstance(context).getReadableDatabase();
        Cursor c = db.query(DayTable.TABLE_NAME,        //table name
                            null,                       //return all columns
                            DayTable.COUNTRY_CODE + "=?", //where clause
                            new String[] { String.valueOf(countryCode) },           //the arguments for where
                            null,
                            null,
                            null
        );
        if(c != null){
            c.moveToFirst();
            while(!c.isAfterLast()){
                Day day = cursorToDay(c);
                days.add(day);
                c.moveToNext();
            }
            c.close();
        }
        return days;
    }

    private Day cursorToDay(Cursor c){
        Long id = c.getLong(c.getColumnIndex(DayTable._ID));
        Date date = DateUtils.stringToDate(c.getString(c.getColumnIndex(DayTable.DATE)));
        String localName = c.getString(c.getColumnIndex(DayTable.LOCAL_NAME));
        String englishName = c.getString(c.getColumnIndex(DayTable.ENGLISH_NAME));
        String notes = c.getString(c.getColumnIndex(DayTable.NOTES));
        String countryCode = c.getString(c.getColumnIndex(DayTable.COUNTRY_CODE));
        return new Day(id, date, localName, englishName, notes, countryCode);
    }
}
