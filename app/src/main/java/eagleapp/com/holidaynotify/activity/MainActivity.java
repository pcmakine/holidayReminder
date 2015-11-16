package eagleapp.com.holidaynotify.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.activity.preferences.SettingsActivity;
import eagleapp.com.holidaynotify.db.dao.DayDao;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.JsonParamsHandler;
import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;
import eagleapp.com.holidaynotify.httprequest.enrico.EnricoService;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;
import eagleapp.com.holidaynotify.notification.AlarmScheduler;

public class MainActivity extends AppCompatActivity implements HttpResultListener {
    public static final String TAG = MainActivity.class.getName();
    private EnricoService enricoService;
    private DayListAdapter<Day> arrAdapter;
    private SortedSet<Day> holidays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setAlarm(View view){
        Day day = DayDao.getInstance().loadFirst(this);
        List<Day> days = new ArrayList<>();
        days.add(day);
        AlarmScheduler.scheduleNotificationForAllActiveHolidays(days);
    }

    public void cancelAlarm(View view){
        AlarmScheduler.cancelNotification(null);
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.enricoService = new EnricoService(this);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date fromDate = cal.getTime();
        cal.add(Calendar.YEAR, 4);
        Date toDate = cal.getTime();
        enricoService.getHolidaysForDateRange(fromDate, toDate);
    }

    protected void onStop(){
        if(enricoService != null){
            enricoService.stopRequests();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateCountryList(String response){
        List<Country> countries = JsonParser.parseCountries(response);
        if(countries != null){
            Log.d(TAG, "countries: " + countries.toString());
        }else{
            Log.d(TAG, "update countrylist called, but no countries returned by the parser");
        }
    }

    private void updateDateList(String response, String url){
        List<Day> days = JsonParser.parseJson(response, url);
        Collections.sort(days);

        if(holidays == null){
            holidays = new TreeSet<Day>();
        }
        for(Day day: days){
            if(day.getDate() == new Date() || day.getDate().after(new Date())){
                if(!holidays.contains(day)){
                    holidays.add(day);
                }
            }
        }
        List<Day> displayDays = new ArrayList<>();
        displayDays.addAll(holidays);
        ListView list = (ListView)findViewById(R.id.list);
        if(arrAdapter == null ){
            arrAdapter = new DayListAdapter<Day>(holidays, this);
        }else{
            arrAdapter.addAll(displayDays);
        }
        list.setAdapter(arrAdapter);
    }

    @Override
    public void onResponse(String response, String url) {
        if(JsonParamsHandler.findTokensByKey(EnricoService.BASE_URL, url, "&", EnricoParams.Keys.ACTION).contains(EnricoParams.Actions.SUPPORTED_COUNTRIES_LIST)){
            updateCountryList(response);
        }else{
            updateDateList(response, url);   //TODO change this to update to the database
        }
    }

    @Override
    public void onErrorResult(String result, String url) {
        onResponse(result, url);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
