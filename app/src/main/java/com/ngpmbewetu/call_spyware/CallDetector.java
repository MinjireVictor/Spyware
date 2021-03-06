package com.ngpmbewetu.call_spyware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

public class CallDetector {

    public static final String MY_PREF = "MY_PREF";
    public static final String NUMBER_KEY = "NUMBER_KEY";

    public class OutgoingDetector  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NUMBER_KEY,number);
            editor.apply();
        }

    }

    private Context ctx;
    private OutgoingDetector outgoingDetector;

    public CallDetector(Context ctx) {
        this.ctx = ctx;
        outgoingDetector = new OutgoingDetector();
    }

    public void start() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingDetector, intentFilter);
    }

    public void stop(){
        ctx.unregisterReceiver(outgoingDetector);
    }

}
