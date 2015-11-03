package eagleapp.com.holidaynotify.httprequest;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;

/**
 * Created by Pete on 3.11.2015.
 */
public class HttpRequest {
    public static final String URL = "http://httpbin.org/html";
    public static final String TAG = HttpRequest.class.getName();
    private WeakReference<HttpResultListener> resultListener;
    private RequestQueue queue;

    public HttpRequest(HttpResultListener resultListener){
        this.resultListener = new WeakReference(resultListener);
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
