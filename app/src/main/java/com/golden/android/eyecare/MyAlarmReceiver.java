package com.golden.android.eyecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Machine on 15-08-2016.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    //public static final String ACTION = "com.codepath.example.servicesdemo.alarm";
    String TAG ="MyAlarm REciver";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, Checker.class);
     //   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("foo", "bar");
        Log.i(TAG, "onReceive: Checking called");
        context.startService(i);

//        Intent Timer = new Intent(context, Timer.class);
//       // Timer.addFlags(Intent.)
//        context.startService(Timer);

    }
}

