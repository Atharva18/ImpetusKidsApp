package com.example.parij.myschoolcomm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by admin on 30/12/2017.
 */

public class SessionManagement {
    static String username;
    static long lastLoginTimestamp;
    static boolean rememberMe;
    static boolean isAdmin;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public static void retrieveSharedPreferences(Context context)
    {
        sharedPreferences = context.getSharedPreferences("ImpetusKids",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","NA");
        lastLoginTimestamp = sharedPreferences.getLong("lastLoginTimpstamp",0);
        rememberMe = sharedPreferences.getBoolean("rememberMe",false);
        isAdmin = sharedPreferences.getBoolean("isAdmin", false);
    }
    public static void updateSharedPreferences()
    {
        if(sharedPreferences != null)
        {
            editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.putLong("lastLoginTimpstamp",lastLoginTimestamp);
            editor.putBoolean("rememberMe",rememberMe);
            editor.putBoolean("isAdmin", isAdmin);
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
