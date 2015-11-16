package eagleapp.com.holidaynotify.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.domain.Day;

/**
 * Created by Pete on 16.11.2015.
 */
public class AlarmScheduler {
    public static final String TAG = NotificationService.class.getName();
    public static final String HOLIDAY_ID_KEY = "holidayId";

    public static void scheduleNotificationForAllActiveHolidays(List<Day> days){
        AlarmManager am = (AlarmManager) HolidayNotify.context.getSystemService(Context.ALARM_SERVICE);
        for(Day day: days){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day.getDate());
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), createPendingIntent(day));
        }
    }

    public static void cancelAllNotificationsFor(List<Day> days){
        for (Day day: days){
            cancelNotification(day);
        }
    }

    public static void cancelNotification(Day day){
        PendingIntent sender = createPendingIntent(day);
        AlarmManager alarmManager = (AlarmManager) HolidayNotify.context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private static PendingIntent createPendingIntent(Day day){
        Log.d(TAG, "creating pending intent, day id: " + day.getId());
        Intent intent = new Intent(HolidayNotify.context, AlarmReceiver.class);
        intent.putExtra(HOLIDAY_ID_KEY, day.getId());
        return PendingIntent.getBroadcast(HolidayNotify.context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
