package eagleapp.com.holidaynotify.activity.preferences;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.activity.AppCompatPreferenceActivity;
import eagleapp.com.holidaynotify.activity.HolidayNotify;
import eagleapp.com.holidaynotify.db.dao.CountryDao;
import eagleapp.com.holidaynotify.db.dao.DayDao;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;
import eagleapp.com.holidaynotify.httprequest.enrico.actions.YearHolidays;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity implements HttpResultListener {
    public static final String TAG = SettingsActivity.class.getName();
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private HttpRequest request;
    private final String requestTag = "httpRequest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_countries);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void downloadHolidayDaysForYear(String countryCode, int year){
        YearHolidays enricoAction = new YearHolidays();
        enricoAction.setCountryCode(countryCode);
        enricoAction.setYear(Calendar.getInstance().get(Calendar.YEAR));
        this.request = new HttpRequest(this, enricoAction.buildParamsMap());
        this.request.sendJsonRequest(requestTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if( key.equals(getResources().getString(R.string.preference_country_selection_key)) ){
                    Preference pref = findPreference(getResources().getString(R.string.preference_country_selection_key));
                    String defaultCountryCode = CountryDao.getInstance().loadFirst(HolidayNotify.context).getCountryCode();
                    String countryCode = prefs.getString(key, defaultCountryCode);

                    //String countryCode = prefs.getString(key, "");
                    System.out.println("listener called!!!!!!!");
                    Log.d(TAG, "preference changed, new value: " + countryCode);
                    List<Day> days = new ArrayList<>();
                    days = DayDao.getInstance().loadByCountry(HolidayNotify.context, countryCode);
                    if(days == null || days.isEmpty()){
                        downloadHolidayDaysForYear(countryCode, Calendar.getInstance().get(Calendar.YEAR));
                    }

                }
            }
        };
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onStop(){
        if( request != null && request.getQueue() != null ){
            request.getQueue().cancelAll(requestTag);
        }
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(String result, String url) {
        List<Day> days = JsonParser.parseJson(result, url);
        Collections.sort(days);
        DayDao.getInstance().insertMany(this, days);
    }

    @Override
    public void onErrorResult(String result, String url) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
