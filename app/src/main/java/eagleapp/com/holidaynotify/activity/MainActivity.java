package eagleapp.com.holidaynotify.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.activity.preferences.SettingsActivity;
import eagleapp.com.holidaynotify.db.dao.CountryDao;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.domain.Day;
import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.JsonParamsHandler;
import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;
import eagleapp.com.holidaynotify.httprequest.enrico.actions.YearHolidays;

public class MainActivity extends AppCompatActivity implements HttpResultListener {
    public static final String TAG = MainActivity.class.getName();
    private HttpRequest request;
    private final String requestTag = "httpRequest";


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

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultCountryCode = CountryDao.getInstance().loadFirst(this).getCountryCode();
        String countryCode = preferences.getString(getResources().getString(R.string.preference_country_selection_key), defaultCountryCode);
        Log.d(TAG, "Selected country code: " + countryCode);

        YearHolidays enricoAction = new YearHolidays();
        enricoAction.setCountryCode(countryCode);
        // enricoAction.setRegion("Helsinki");
        enricoAction.setYear(Calendar.getInstance().get(Calendar.YEAR));
        this.request = new HttpRequest(this, enricoAction.buildParamsMap());
        this.request.sendJsonRequest(requestTag);

      /*  SupportedCountries countryListAction = new SupportedCountries();
        this.request.setParams(countryListAction.buildParamsMap());
        this.request.sendJsonRequest(requestTag);*/
    }

    protected void onStop(){
        if( request != null && request.getQueue() != null ){
            request.getQueue().cancelAll(requestTag);
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

    private void updateDateList(String response){
        List<Day> days = JsonParser.parseJson(response);
        Collections.sort(days);
        List<String> daysStr = new ArrayList<String>();
        for(Day day: days){
            daysStr.add(day.toString());
        }
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daysStr);
        list.setAdapter(arrAdapter);
    }

    @Override
    public void onResponse(String response, String url) {
        if(JsonParamsHandler.findTokensByKey(request.BASE_URL, url, "&", EnricoParams.Keys.ACTION).contains(EnricoParams.Actions.SUPPORTED_COUNTRIES_LIST)){
            updateCountryList(response);
        }else{
            updateDateList(response);   //TODO change this to update to the database
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
