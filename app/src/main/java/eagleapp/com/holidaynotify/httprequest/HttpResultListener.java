package eagleapp.com.holidaynotify.httprequest;

import android.content.Context;

/**
 * Created by Pete on 3.11.2015.
 */
public interface HttpResultListener {

    public void onResponse(String result);
    public void onErrorResult(String result);
    public Context getContext();
}
