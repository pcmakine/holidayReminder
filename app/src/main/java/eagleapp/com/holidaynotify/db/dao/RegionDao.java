package eagleapp.com.holidaynotify.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.RegionTable;
import eagleapp.com.holidaynotify.domain.Region;

/**
 * Created by Pete on 11.11.2015.
 */
public class RegionDao {
    public static final String TAG = RegionDao.class.getName();
    private static RegionDao instance = new RegionDao();

    private RegionDao(){
    }

    public static RegionDao getInstance(){
        return instance;
    }

    public Long insertOne(Context context, Region region){
        SQLiteDatabase db = DbHandler.getInstance(context).getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(RegionTable.NAME, region.getName());
        vals.put(RegionTable.COUNTRY_CODE, region.getCountryCode());
        Long id = db.insert(RegionTable.TABLE_NAME, null, vals);
        db.close();
        return id;
    }

    public String insertMany(Context context, Collection<Region> regions){
        String errors = "";
        for(Region region: regions){
            insertOne(context, region);
        }
        return errors;
    }

    public Region loadById(Long id){
        return executeQuery(RegionTable._ID + "=?", new String[]{String.valueOf(id)});
    }

    public Region loadByNameAndCountryCode(String name, String countryCode){
        return executeQuery(RegionTable.NAME + "=? AND " + RegionTable.COUNTRY_CODE + "=?", new String[] { name, countryCode });
    }

    //TODO create a dao class which all daos extend. Move this method in that class and give as parameter everything contentvalue takes
    //Maybe create a similar method for executing rawquery, or make some kind of a template method.
    private Region executeQuery(String whereClause, String[] whereArgs){
        Region region = null;
        SQLiteDatabase db = DbHandler.getInstance(HolidayNotify.context).getReadableDatabase();
        Cursor c = db.query(RegionTable.TABLE_NAME,        //table name
                null,                       //return all columns
                whereClause,                //where clause
                whereArgs,                  //the arguments for where
                null,                       //groupby
                null,                       //having
                null                        //orderby
        );
        if(c != null){
            c.moveToFirst();
            if(!c.isAfterLast()){
                region = cursorToRegion(c);
            }
            c.close();
        }
        db.close();
        return region;
    }

    private Region cursorToRegion(Cursor c){
        Long id = c.getLong(c.getColumnIndex(RegionTable._ID));
        String name = c.getString(c.getColumnIndex(RegionTable.NAME));
        String countryCode = c.getString(c.getColumnIndex(RegionTable.COUNTRY_CODE));
        return new Region(id, name, countryCode);
    }
}
