package com.example.parij.myschoolcomm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by admin on 30/12/2017.
 */

public class SessionManagement {
    static String username;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public static void retrieveSharedPreferences(Context context)
    {
        sharedPreferences = context.getSharedPreferences("ImpetusKids",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","NA");
    }
    public static void updateSharedPreferences()
    {
        if(sharedPreferences != null)
        {
            editor = sharedPreferences.edit();
            editor.putString("username", username);
            if(editor.commit())
            {
                Log.e("SP","Commit successfull with Uname : " + username);
            }
        }
        else
        {
            Log.e("SP","SP was null");
        }
    }
}
