package eagleapp.com.holidaynotify.httprequest;

import android.content.Context;

/**
 * Created by Pete on 3.11.2015.
 */
public interface HttpResultListener {

    public void onResponse(String result, String url);
    public void onErrorResult(String result, String url);
    public Context getContext();
}
