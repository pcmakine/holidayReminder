package eagleapp.com.holidaynotify.activity;

import android.content.Context;

/**
 * Created by Pete on 9.11.2015.
 */
public class HolidayNotify extends android.app.Application {
    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
}
