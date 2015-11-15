package eagleapp.com.holidaynotify.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.db.DbHandler;
import eagleapp.com.holidaynotify.db.table.RegionTable;

/**
 * Created by Pete on 14.11.2015.
 */
public abstract class Dao<T> {
    protected String tableName;

    protected List<T> executeRawQuery(String query, String[] selection){
        List<T> retList = new ArrayList<>();
        SQLiteDatabase db = DbHandler.getInstance(HolidayNotify.context).getReadableDatabase();
        System.out.println("trying to execute a raw query. The query is: " + query);

        Cursor c = db.rawQuery(query, selection);
        if(c != null){
            c.moveToFirst();
            while(!c.isAfterLast()){
                T retObj = cursorToDomainObject(c);
                retList.add(retObj);
                c.moveToNext();
            }
            c.close();
        }
        db.close();
        return retList;
    }

    protected T executeQuery(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        T ret = null;
        SQLiteDatabase db = DbHandler.getInstance(HolidayNotify.context).getReadableDatabase();
        Cursor c = db.query(RegionTable.TABLE_NAME,        //table name
                null,                       //return all columns
                selection,                //where clause
                selectionArgs,                  //the arguments for where
                null,                       //groupby
                null,                       //having
                null                        //orderby
        );
        if(c != null){
            c.moveToFirst();
            if(!c.isAfterLast()){
                ret = cursorToDomainObject(c);
            }
            c.close();
        }
        db.close();
        return ret;
    }

    protected abstract T cursorToDomainObject(Cursor c);
}
