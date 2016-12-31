package com.golden.android.eyecare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by Machine on 15-08-2016.
 */
public class RepeatingAlarmFragment extends Fragment {

    // This value is defined and consumed by app code, so any value will work.
    // There's no significance to this sample using 0.
    public static final int REQUEST_CODE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(true) {

            // BEGIN_INCLUDE (intent_fired_by_alarm)
            // First create an intent for the alarm to activate.
            // This code simply starts an Activity, or brings it to the front if it has already
//            // been created.
//            Intent intent = new Intent(getActivity(), Checker.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Construct an intent that will execute the AlarmReceiver
            Intent intent = new Intent(getActivity(), MyAlarmReceiver.class);
            // Create a PendingIntent to be triggered when the alarm goes off
            final PendingIntent pIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(), MyAlarmReceiver.REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);


            //     intent.setAction(Alert2.WINDOW_SERVICE);
            //   intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            // END_INCLUDE (intent_fired_by_alarm)

            // BEGIN_INCLUDE (pending_intent_for_alarm)
            // Because the intent must be fired by a system service from outside the application,
            // it's necessary to wrap it in a PendingIntent.  Providing a different process with
            // a PendingIntent gives that other process permission to fire the intent that this
            // application has created.
            // Also, this code creates a PendingIntent to start an Activity.  To create a
            // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
//            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), REQUEST_CODE,
//                    intent, 0);
//            PendingIntent pendingIntent = PendingIntent.getService(getActivity(), REQUEST_CODE,
//                    intent,0);

            // END_INCLUDE (pending_intent_for_alarm)

            // BEGIN_INCLUDE (configure_alarm_manager)
            // There are two clock types for alarms, ELAPSED_REALTIME and RTC.
            // ELAPSED_REALTIME uses time since system boot as a reference, and RTC uses UTC (wall
            // clock) time.  This means ELAPSED_REALTIME is suited to setting an alarm according to
            // passage of time (every 15 seconds, 15 minutes, etc), since it isn't affected by
            // timezone/locale.  RTC is better suited for alarms that should be dependant on current
            // locale.

            // Both types have a WAKEUP version, which says to wake up the device if the screen is
            // off.  This is useful for situations such as alarm clocks.  Abuse of this flag is an
            // efficient way to skyrocket the uninstall rate of an application, so use with care.
            // For most situations, ELAPSED_REALTIME will suffice.
            int alarmType = AlarmManager.ELAPSED_REALTIME;

            final int FIFTEEN_SEC_MILLIS = 120;

            // The AlarmManager, like most system services, isn't created by application code, but
            // requested from the system.
            AlarmManager alarmManager = (AlarmManager)
                    getActivity().getSystemService(getActivity().ALARM_SERVICE);

            // setRepeating takes a start delay and period between alarms as arguments.
            // The below code fires after 20, and r minutrs repeats every 20  min seconds.  This is very
            // useful for demonstration purposes, but horrendous for production.  Don't be that dev.

//            alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
//                    FIFTEEN_SEC_MILLIS, pIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                    FIFTEEN_SEC_MILLIS, pIntent);


            // END_INCLUDE (configure_alarm_manager);
            Log.i("RepeatingAlarmFragment", "Alarm set.");
        }
        return true;
    }
}

