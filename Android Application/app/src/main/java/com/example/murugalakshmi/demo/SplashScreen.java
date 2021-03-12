package com.example.murugalakshmi.demo;

import android.content.Context;
import android.content.SharedPreferences;

public class SplashScreen {
    private SharedPreferences pref;
    private Context context;
    public SplashScreen(Context context){

        this.context=context;
        pref=context.getSharedPreferences(context.getResources().getString(R.string.login_preferences),Context.MODE_PRIVATE);

    }
    public  void  writeLoginStatus(boolean status){
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean(context.getResources().getString(R.string.login_status_preferences), true);
        edt.commit();
    }
    public boolean readLoginStatus(){
        boolean status = false;
        status=pref.getBoolean(context.getResources().getString(R.string.login_status_preferences),false);
        return status;
    }
    public  void saveDevicename(String name){
        SharedPreferences.Editor edtname = pref.edit();
        edtname.putString(context.getResources().getString(R.string.fetch_devicename),name);
        edtname.commit();

    }
    public String readDevicename(){
        String value=pref.getString(context.getResources().getString(R.string.fetch_devicename),null);
        return value;
    }
}
