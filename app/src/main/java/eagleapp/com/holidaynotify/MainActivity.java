package eagleapp.com.holidaynotify;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.enrico.Action;

public class MainActivity extends AppCompatActivity implements HttpResultListener {

    public static final String TAG = MainActivity.class.getName();
    private TextView responseTW;
    private HttpRequest request;
    private final String requestTag = "dayRequests";


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

        responseTW = (TextView) findViewById(R.id.responseTW);
        responseTW.setText(Action.monthHolidays.toString());
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.request = new HttpRequest(this);
        this.request.sendJsonRequest(requestTag);
    }

    protected void onStop(){
        if( request.getQueue() != null ){
            Log.d(TAG, "onstop method");
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(String result) {
        responseTW.setText(result);
    }

    @Override
    public void onErrorResult(String result) {
        onResponse(result);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
