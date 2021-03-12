package com.example.murugalakshmi.demo;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

//import static com.example.murugalakshmi.demo.App.CHANNEL_ID;


public  class MyService extends Service {
    int values;

    public void onCreate() {
        super.onCreate();
    }


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //return null;
        throw new UnsupportedOperationException("Not yet implemented");
    }

    String input;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        input = intent.getStringExtra("Devicename");

        BroadcastReceiver br = new BroadcastReceiver() {
            int scale = -1;
            int level = -1;
            int voltage = -1;
            int temp = -1;

            @Override
            public void onReceive(Context context, Intent intent) {
                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                values = level;
              new MyService.DataAsyncTask().execute();
               // Toast.makeText(MyService.this, "battery" + level , Toast.LENGTH_SHORT).show();

               /* final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(input);
                myRef.setValue(values);*/
                //Log.v("hello", String.valueOf(level));
            }
        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br, filter);

        return START_STICKY;


    }

    public class DataAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(input);
               // Thread.sleep(100000);
                if (values>=95)
                {
                    myRef.setValue("False");
                }
                else if (values<=25)
                {
                    myRef.setValue("True");
                }



            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
            return null;
        }

    }
}







