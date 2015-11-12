package eagleapp.com.holidaynotify.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import eagleapp.com.holidaynotify.R;
import eagleapp.com.holidaynotify.db.dao.CountryDao;
import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;
import eagleapp.com.holidaynotify.httprequest.enrico.actions.SupportedCountries;

public class Splash extends AppCompatActivity implements HttpResultListener{
    public static final String TAG = MainActivity.class.getName();
    // On first start up show the splash screen for at least two seconds
    private static int SPLASH_MIN_TIME = 3000;
    //The time when the activity was started
    private static long startTime;
    private final String requestTag = "httpRequest";
    private HttpRequest request;
    private AsyncTask countryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadIndicator);
        progressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(this, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void startMainActivity(){
        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void startMainActivityAfterMinTimePassed(){
        long timeLeft = SPLASH_MIN_TIME - (System.currentTimeMillis() - startTime);
        if(timeLeft > 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMainActivity();
                }
            }, timeLeft);
        }else{
            startMainActivity();
        }
    }

    private boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    @Override
    protected void onResume(){
        super.onResume();
        this.startTime = System.currentTimeMillis();
        if(isConnectedToInternet()){
            SupportedCountries countryListAction = new SupportedCountries();
            this.request = new HttpRequest(this, countryListAction.buildParamsMap());
            this.request.sendJsonRequest(requestTag);
        }else{
            Toast.makeText(this, "Internet connection not available. Going to update the supported " +
                    "country list next time the connection is available again.", Toast.LENGTH_LONG).show();
            startMainActivity();
        }
    }

    @Override
    protected void onPause(){
        if(countryTask != null) {
            countryTask.cancel(true);
            countryTask = null;
        }
        super.onPause();
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    private void updateCountryList(String response) {
        List<Country> countries = JsonParser.parseCountries(response);
        if(countries != null && !countries.isEmpty()){
            Log.d(TAG, "countries: " + countries.toString());
            Log.d(TAG, "saving countries to db...");
            //CountryDao.getInstance().insertMany(this, countries);      //todo maybe move this to asynctask
            countryTask = new InsertCountriesTask();
            countryTask.execute(countries.toArray(new Country[countries.size()]));
        }else{
            Log.d(TAG, "update countrylist called, but no countries returned by the parser");
        }
    }
    @Override
    public void onResponse(String result, String url) {
        if( result != null){
            updateCountryList(result);
        }
  /*      long timeLeft = SPLASH_MIN_TIME - (System.currentTimeMillis() - startTime);
        Log.d(TAG, "Fetching the countrylist took " + (System.currentTimeMillis() - startTime)+
                " ms. Time left: " + timeLeft + " ms");
        startMainActivityAfterMinTimePassed();*/
    }

    @Override
    protected void onStop(){
        if( request != null && request.getQueue() != null ){
            request.getQueue().cancelAll(requestTag);
        }
        super.onStop();
    }

    @Override
    public void onErrorResult(String result, String url) {
    }

    @Override
    public Context getContext() {
        return this;
    }

    private class InsertCountriesTask extends AsyncTask<Country, Void, String> {

        @Override
        protected String doInBackground(Country... params) {
            CountryDao.getInstance().insertMany(HolidayNotify.context, Arrays.asList(params));
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            startMainActivityAfterMinTimePassed();
        }
    }
}
