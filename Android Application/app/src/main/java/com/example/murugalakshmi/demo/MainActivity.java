package com.example.murugalakshmi.demo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murugalakshmi.demo.R;
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


public class MainActivity extends AppCompatActivity
{
   // private EditText editTextInput;
    public SplashScreen sscreenobj;
    public RadioGroup rg;
    public Switch s;
    public FirebaseDatabase db;
    DatabaseReference myRef;
    String cname;
    //public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sscreenobj= new  SplashScreen(getApplicationContext());
        //editTextInput = findViewById(R.id.editText);
        s=findViewById(R.id.switch1);
        //btn=findViewById(R.id.btn);
        db = FirebaseDatabase.getInstance();
        rg = (RadioGroup) findViewById(R.id.rg);

        //rg.clearCheck();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (checkedId == R.id.mobile)
                    {
                        s.setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.textView)).setVisibility(View.INVISIBLE);
                        ((TextInputEditText) findViewById(R.id.editHH)).setVisibility(View.INVISIBLE);
                        ((TextInputEditText) findViewById(R.id.editMM)).setVisibility(View.INVISIBLE);
                        //Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        s.setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.textView)).setVisibility(View.VISIBLE);
                        ((EditText) findViewById(R.id.editHH)).setVisibility(View.VISIBLE);
                        ((EditText) findViewById(R.id.editMM)).setVisibility(View.VISIBLE);
                    }
                    cname = String.valueOf(rb.getText());
                    myRef = db.getReference(cname);
                    // Log.i("CNAME", cname);
                }
            }
            });
    }

    public void startService(View v) throws InterruptedException {
            if (cname.equals("Mobile")) {
                Boolean statusSwitch1;
                if (s.isChecked()) {

                    myRef.setValue("True");
                } else {
                    myRef.setValue("False");
                }
            }
            else {
                // myRef = db.getReference("Duration");
                EditText hours, minutes;
                hours = (EditText) findViewById(R.id.editHH);
                minutes = (EditText) findViewById(R.id.editMM);
                int duration = Integer.valueOf(hours.getText().toString())*60 + Integer.valueOf(minutes.getText().toString());
                // myRef.setValue("True");
                // Thread.sleep(1000 * duration * 60);
                // myRef.setValue("False");
                myRef.setValue(duration);

            }


        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("Devicename",cname);
        Log.i("CNAME", cname);
        // startForegroundService(serviceIntent);
        startService(serviceIntent);
            sscreenobj.saveDevicename(cname);
            //startActivity(intent);
            sscreenobj.writeLoginStatus(true);
            finish();
     }
}