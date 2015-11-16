package eagleapp.com.holidaynotify.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.activity.MainActivity;
import eagleapp.com.holidaynotify.db.dao.DayDao;
import eagleapp.com.holidaynotify.domain.Day;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {
    public static final String TAG = NotificationService.class.getName();

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // At this point AlarmReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.
        Long id = intent.getLongExtra(AlarmScheduler.HOLIDAY_ID_KEY, -1);
        Log.d(TAG, "day id: " + intent.getLongExtra(AlarmScheduler.HOLIDAY_ID_KEY, -1));
        if(id != -1){
            Day day = DayDao.getInstance().loadById(id);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_event_black_24dp)
                            .setContentTitle("Holiday reminder")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("test text"))
                            .setContentText(day.toString() + " on " + DateFormat.getInstance().format(day.getDate()));
            mBuilder.setContentIntent(createPendingIntent(day.getId()));
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(0, mBuilder.build());
        }
        AlarmReceiver.completeWakefulIntent(intent);

    }

    private PendingIntent createPendingIntent(Long id){
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Because clicking the notification launches a new ("special") activity,
        // there's no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        id.intValue(),
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        return resultPendingIntent;
    }
}
