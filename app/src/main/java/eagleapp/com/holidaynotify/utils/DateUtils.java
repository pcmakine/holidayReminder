package eagleapp.com.holidaynotify.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import eagleapp.com.holidaynotify.db.DbHandler;

/**
 * Created by Pete on 9.11.2015.
 */
public class DateUtils {
    public static final String TAG = DateUtils.class.getName();
    public static String dateToString(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DbHandler.DATE_PATTERN, Locale.ENGLISH);
        return df.format(date);
    }

    public static Date stringToDate(String dateStr){
        SimpleDateFormat df = new SimpleDateFormat(DbHandler.DATE_PATTERN, Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            Log.e(TAG, "error in stringToDate method, cannot parse date");
            e.printStackTrace();
        }
        return date;
    }
}
