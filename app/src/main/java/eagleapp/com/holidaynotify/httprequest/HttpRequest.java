package eagleapp.com.holidaynotify.httprequest;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import eagleapp.com.holidaynotify.dao.Day;
import eagleapp.com.holidaynotify.httprequest.enrico.Action;
import eagleapp.com.holidaynotify.httprequest.enrico.JsonParser;

/**
 * Created by Pete on 3.11.2015.
 */
public class HttpRequest {
    public static final String BASE_URL = "http://www.kayaposoft.com/enrico/json/v1.0/?";
    public static final String URL = "http://httpbin.org/html";
    public static final String TAG = HttpRequest.class.getName();
    private WeakReference<HttpResultListener> resultListener;
    private RequestQueue queue;

    public HttpRequest(HttpResultListener resultListener){
        this.resultListener = new WeakReference(resultListener);
    }

    public void sendJsonRequest(String tag){
        String url = BASE_URL + "action=getPublicHolidaysForMonth&month=5&year=2013&country=fin&region=Helsinki";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(resultListener.get() == null){
                            Log.d(TAG, "listener null in receiving http response");
                        }else{
                            List<Day> days = JsonParser.parseJson(response);
                            Log.d(TAG, "days: " + days.toString());
                            resultListener.get().onResponse(response.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        Log.d(TAG, "got error response in http request " + error.getMessage());
                        error.printStackTrace();

                    }
                });
        jsonArrayRequest.setTag(tag);
        HttpResultListener listener = resultListener.get();
        if(listener == null){
            Log.d(TAG, "listener null in receiving http response");
        }else{
            // Add the request to the queue
            queue = Volley.newRequestQueue(listener.getContext().getApplicationContext());
            queue.add(jsonArrayRequest);
        }

    }

    public void sendRequest(String tag){
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        System.out.println(response.substring(0, 100));
                        if(resultListener.get() == null){
                            Log.d(TAG, "listener null in receiving http response");
                        }else{
                            resultListener.get().onResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                Log.d(TAG, "got error response in http request " + error.getMessage());
                error.printStackTrace();
            }
        });
        stringRequest.setTag(tag);
        HttpResultListener listener = resultListener.get();
        if(listener == null){
            Log.d(TAG, "listener null in receiving http response");
        }else{
            // Add the request to the queue
            queue = Volley.newRequestQueue(listener.getContext().getApplicationContext());
            queue.add(stringRequest);
        }

    }

    public RequestQueue getQueue(){
        return this.queue;
    }
}
