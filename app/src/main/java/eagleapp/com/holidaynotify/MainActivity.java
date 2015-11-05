package eagleapp.com.holidaynotify;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import eagleapp.com.holidaynotify.dao.Day;
import eagleapp.com.holidaynotify.httprequest.HttpRequest;
import eagleapp.com.holidaynotify.httprequest.HttpResultListener;
import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;

public class MainActivity extends AppCompatActivity implements HttpResultListener {

    public static final String TAG = MainActivity.class.getName();
    private TextView responseTW;
    private HttpRequest request;
    private final String requestTag = "dayRequests";
    private String[] testData = new String[]{"test1", "test2", "test3"};


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

        //responseTW = (TextView) findViewById(R.id.responseTW);
    }

    @Override
    protected void onResume(){
        super.onResume();
        LinkedHashMap<String, String> params = EnricoParams.buildParamsMap(
                EnricoParams.Actions.KEY,
                EnricoParams.Actions.Values.MONTH_HOLIDAYS,
                EnricoParams.Keys.MONTH, "5",
                EnricoParams.Keys.YEAR, "2016",
                EnricoParams.Keys.COUNTRY, "fin",
                EnricoParams.Keys.REGION, "Helsinki"
        );
        this.request = new HttpRequest(this, params);
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
    public void onResponse(String response) {
        List<Day> days = JsonParser.parseJson(response);
        List<String> daysStr = new ArrayList<String>();
        for(Day day: days){
            daysStr.add(day.toString());
        }
        ListView list = (ListView)findViewById(R.id.list);
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daysStr);
        list.setAdapter(arrAdapter);
        //responseTW.setText(result);
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
