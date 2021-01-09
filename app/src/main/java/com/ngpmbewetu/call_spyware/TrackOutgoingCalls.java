package com.ngpmbewetu.call_spyware;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrackOutgoingCalls extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private TextView textView;
    private boolean detecting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_outgoing_calls);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, CallDetectorService.class);
        if(!detecting){
            detecting=true;
            button.setText("Turn off detection");
            startService(intent);
        }else{
            detecting=false;
            button.setText("Turn on detection");
            stopService(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(CallDetector.MY_PREF,MODE_PRIVATE);
        String number = sharedPreferences.getString(CallDetector.NUMBER_KEY,"No Value Found");
        textView.setText(number);
    }
}
