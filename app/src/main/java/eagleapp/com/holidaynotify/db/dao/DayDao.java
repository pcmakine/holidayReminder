package eagleapp.com.holidaynotify.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.DayTable;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.utils.DateUtils;

/**
 * Created by Pete on 5.11.2015.
 */
public class DayDao {

    public List<Day> loadByCountry(Context context, Country country){
        List<Day> days = new ArrayList<>();
        SQLiteDatabase db = DbHandler.getInstance(context).getReadableDatabase();
        Cursor c = db.query(DayTable.TABLE_NAME,        //table name
                            null,                       //return all columns
                            DayTable.COUNTRY_ID + "=?", //where clause
                            new String[] { String.valueOf(country.getId()) },           //the arguments for where
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
        return new Day(id, date, localName, englishName, notes);
    }
}
