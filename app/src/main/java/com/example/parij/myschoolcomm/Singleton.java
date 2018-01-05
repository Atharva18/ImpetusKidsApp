package com.example.parij.myschoolcomm;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 13/10/2017.
 */

public class Singleton {
    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    //Constructor
    private Singleton(Context context){
        mCtx = context; //Initialize context.
        requestQueue = getRequestQueue(); //Call the method.
    }

    //Request queue method.
    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            //Initialize request queue.
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    //This method returns instance of this class.
    public static synchronized Singleton getInstance(Context context){
        if (mInstance == null){
            //Initialize instance
            mInstance = new Singleton(context);
        }
        return mInstance;
    }

    //This method is to add request queue.
    public <T> void addToRequestque(Request<T> request){
        //Add each of the request to request queue.
        requestQueue.add(request);
    }
}