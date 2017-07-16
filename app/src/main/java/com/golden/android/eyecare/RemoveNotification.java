package com.golden.android.eyecare;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class RemoveNotification extends Service {
    public RemoveNotification() {


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent intent1 = new Intent(getApplicationContext(), CustomFloatingViewService.class);
        intent1.addCategory("Checker");
        stopService(intent1);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
