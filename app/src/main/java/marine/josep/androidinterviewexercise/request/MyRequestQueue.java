package marine.josep.androidinterviewexercise.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyRequestQueue {

    private static Context context;
    private static MyRequestQueue instance;
    private RequestQueue requestQueue;

    private MyRequestQueue(Context context) {
        MyRequestQueue.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MyRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new MyRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
