package com.golden.android.eyecare;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Machine on 15-08-2016.
 */
public class Timer extends IntentService {
    String TAG = "Timer";
    //ScreenReceiver screenReceiver =new ScreenReceiver();

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
//        registerReceiver(screenReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));
        android.util.Log.i(TAG, "onCreate: android");

    }

    @Override
    protected void onHandleIntent(Intent intent) {


//        Intent dialogIntent = new Intent(this, Alert.class);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//
//        startActivity(dialogIntent);


        Log.i(TAG, "onHandleIntent: Timer started");
        Intent intent2 = new Intent(getApplicationContext(), MyAlarmReceiver.class);

        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234,
                intent2, PendingIntent.FLAG_UPDATE_CURRENT);




        int alarmType = AlarmManager.ELAPSED_REALTIME;
        int i=checkTime();
        final long FIFTEEN_SEC_MILLIS = System.currentTimeMillis()+
              // 30000;

        //i*60000;  //minutes
        i*1000;  //20 seconds

        Log.i(TAG, "onHandleIntent: "+i);

        // The AlarmManager, like most system services, isn't created by application code, but
        // requested from the system.
        AlarmManager alarmManager = (AlarmManager)
                getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + FIFTEEN_SEC_MILLIS,
//                FIFTEEN_SEC_MILLIS, pIntent);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean Toggle = sharedPreferences.getBoolean("example_switch",true);

        if (Toggle) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC, FIFTEEN_SEC_MILLIS
                        , pIntent);
            } else {
                alarmManager.set(AlarmManager.RTC, FIFTEEN_SEC_MILLIS
                        , pIntent);

            }
        }
        //Looper.loop();
        Log.i(TAG, "onHandleIntent: running");

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        unregisterReceiver(screenReceiver);
//        Toast.makeText(getApplicationContext(), "unregistered Receiver", Toast.LENGTH_SHORT).show();
//
//    }

    int checkTime(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String s = sharedPreferences.getString("example_list","20");
                //getString("example_list","20");

            Integer i= Integer.parseInt(s);
            return  i;


    }
}
