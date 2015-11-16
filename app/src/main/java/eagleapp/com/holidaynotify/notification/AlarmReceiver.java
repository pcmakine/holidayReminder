package eagleapp.com.holidaynotify.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends WakefulBroadcastReceiver{
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        service.putExtra(AlarmScheduler.HOLIDAY_ID_KEY, intent.getLongExtra(AlarmScheduler.HOLIDAY_ID_KEY, -1));
        // Start the service, keeping the device awake while it is launching.
        Log.i("AlarmReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);
    }
}
