package com.golden.android.eyecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Machine on 15-08-2016.
 */
public class ScreenReceiver extends BroadcastReceiver {
   //ScreenReceiver screen;
    Context context=null;
    String TAG = "ScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context=context;

        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)||intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)||intent.getAction().equals(Intent.ACTION_USER_PRESENT))
        {
            Log.i(TAG, "onReceive: service called ");

            Intent i = new Intent(context, Timer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //   context.startActivity(i);
            context.startService(i);
        }
    }
}
