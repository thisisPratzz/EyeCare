package com.golden.android.eyecare;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Machine on 15-08-2016.
 */
public class Timer extends IntentService {
    String TAG = "Timer";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public Timer() {
        super("Timer");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: Timer started");
        Intent intent2 = new Intent(getApplicationContext(), MyAlarmReceiver.class);

        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), MyAlarmReceiver.REQUEST_CODE,
                intent2, PendingIntent.FLAG_UPDATE_CURRENT);




        int alarmType = AlarmManager.ELAPSED_REALTIME;
        int i=checkTime();
        final int FIFTEEN_SEC_MILLIS =
              // 30000;
        i*1000;
        Log.i(TAG, "onHandleIntent: "+i);

        // The AlarmManager, like most system services, isn't created by application code, but
        // requested from the system.
        AlarmManager alarmManager = (AlarmManager)
                getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                FIFTEEN_SEC_MILLIS, pIntent);
        Looper.loop();
        Log.i(TAG, "onHandleIntent: running");

    }


    int checkTime(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String s = sharedPreferences.getString("example_list","20");
                //getString("example_list","20");

            Integer i= Integer.parseInt(s);
            return  i;


    }
}
