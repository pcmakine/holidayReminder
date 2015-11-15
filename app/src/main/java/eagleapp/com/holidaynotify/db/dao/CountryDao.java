package eagleapp.com.holidaynotify.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.CountryTable;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.utils.DateUtils;

/**
 * Created by Pete on 8.11.2015.
 */
public class CountryDao {
    public static final String TAG = CountryDao.class.getName();
    private static CountryDao instance = new CountryDao();
    private static final String QUERY_SELECT_ALL = "SELECT * FROM " + CountryTable.TABLE_NAME +
            " ORDER BY " + CountryTable.FULL_NAME + " ASC";
    private static final String QUERY_DELETE_ALL = "DELETE FROM " + CountryTable.TABLE_NAME;
    private static final String QUERY_FIRST_ROW = "SELECT * FROM " + CountryTable.TABLE_NAME +
            " ORDER BY " + CountryTable.FULL_NAME + " ASC LIMIT 1";

    private CountryDao(){
    }

    public static CountryDao getInstance(){
        return instance;
    }

    public String insertOne(Context context, Country country){
        String errors = "";
        SQLiteDatabase db = DbHandler.getInstance(context).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(CountryTable.FULL_NAME, country.getFullName());
        vals.put(CountryTable.COUNTRY_CODE, country.getCountryCode());
        vals.put(CountryTable.FROM_DATE, DateUtils.dateToDbString(country.getFrom()));
        vals.put(CountryTable.TO_DATE, DateUtils.dateToDbString(country.getTo()));
        db.insert(CountryTable.TABLE_NAME, null, vals);
        RegionDao.getInstance().insertMany(context, country.getRegions());
        db.close();
        return errors;
    }

    public String insertMany(Context context, List<Country> countries){
        String errors = "";
        for(Country country: countries){
            insertOne(context, country);
        }
        return errors;
    }

    public List<Country> loadAll(Context context){
        List<Country> countries = new ArrayList<>();
        SQLiteDatabase db = DbHandler.getInstance(context).getReadableDatabase();
        Cursor c = db.rawQuery(QUERY_SELECT_ALL, null);
        if(c != null){
            c.moveToFirst();
            while(!c.isAfterLast()){
                Country country = cursorToCountry(c);
                countries.add(country);
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return countries;
    }

    public List<String> loadAllCodes(){
        List<Country> countries = loadAll(HolidayNotify.context);
        List<String> codes = new ArrayList<>();
        for(Country country: countries){
            codes.add(country.getCountryCode());
        }
        return codes;
    }

    public Country loadFirst(Context context){
        Country country = null;
        SQLiteDatabase db = DbHandler.getInstance(context).getReadableDatabase();
        Cursor c = db.rawQuery(QUERY_FIRST_ROW, null);
        if(c != null) {
            c.moveToFirst();
            country = cursorToCountry(c);
            c.close();
        }
        db.close();
        return country;
    }

    private Country cursorToCountry(Cursor c){
        Long id = c.getLong(c.getColumnIndex(CountryTable._ID));
        String fullName = c.getString(c.getColumnIndex(CountryTable.FULL_NAME));
        String countryCode = c.getString(c.getColumnIndex(CountryTable.COUNTRY_CODE));
        Date fromDate = DateUtils.stringToDate(c.getString(c.getColumnIndex(CountryTable.FROM_DATE)));
        Date toDate = DateUtils.stringToDate(c.getString(c.getColumnIndex(CountryTable.TO_DATE)));
        return new Country(fullName, countryCode, fromDate, toDate, null);
    }

    public void deleteAllRows(Context context){
        SQLiteDatabase db = DbHandler.getInstance(context).getWritableDatabase();
        db.execSQL(QUERY_DELETE_ALL);
        db.close();
    }

}
