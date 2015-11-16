package eagleapp.com.holidaynotify.httprequest.enrico;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Date;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.enrico.actions.HolidaysForDateRange;

/**
 * Created by Pete on 13.11.2015.
 */
public class EnricoService implements HttpResultListener{
    public static final String TAG = EnricoService.class.getName();
    public static final String BASE_URL = HttpRequest.BASE_URL;
    private HttpRequest request;
    private final String requestTag = "httpRequest";
    private WeakReference<HttpResultListener> listener;

    public EnricoService(HttpResultListener listener ){
        this.listener = new WeakReference<HttpResultListener>(listener);
    }

    public void getHolidaysForDateRange(Date fromDate, Date toDate){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HolidayNotify.context);
       // String defaultCountryCode = CountryDao.getInstance().loadFirst(HolidayNotify.context).getCountryCode();
        String countryCode = preferences.getString(HolidayNotify.context.getResources().getString(R.string.preference_country_selection_key), HolidayNotify.context.getResources().getString(R.string.countryCodeDefault));
        Log.d(TAG, "Selected country code: " + countryCode.toString());
        HolidaysForDateRange enricoAction = new HolidaysForDateRange();
        enricoAction.setCountryCode(countryCode);
        enricoAction.setFromDate(fromDate);
        enricoAction.setToDate(toDate);
        this.request = new HttpRequest(this, enricoAction.buildParamsMap());
        this.request.sendJsonRequest(requestTag);


    }

    public void stopRequests(){
        if( request != null ){
            if( request.getQueue() != null){
                request.getQueue().cancelAll(requestTag);
            }
        }
    }

    @Override
    public void onResponse(String result, String url) {
        HttpResultListener activity = listener.get();
        if(activity != null){
            activity.onResponse(result, url);
        }
    }

    @Override
    public void onErrorResult(String result, String url) {
        HttpResultListener activity = listener.get();
        if(activity != null){
            activity.onErrorResult(result, url);
        }
    }

    @Override
    public Context getContext() {
        return HolidayNotify.context;
    }
}
