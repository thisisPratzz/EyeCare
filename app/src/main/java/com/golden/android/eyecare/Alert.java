package com.golden.android.eyecare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Machine on 15-08-2016.
 */
public class Alert extends Activity {
    boolean flag=false;
    Context context;
    String TAG ="Alert";
    Global global ;//=
            //new Global();


    // (Global) getApplicationContext();

    private final BroadcastReceiver killer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: recived kill");

            finish();





            //onBackPressed();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =getApplicationContext();
        global=(Global) getApplicationContext();
        Log.i("ALert", "onCreate:started ");
        registerReceiver(killer, new IntentFilter("killAlert"));
        Log.i(TAG, "onCreate: reciver registered");
        sendBroadcast(new Intent("killerAlert"));

        //    this.finish();
//       Checker check = new Checker();

        displayAlert();
       // Looper.loop();

    }
//    @Override
//    public void onBackPressed() {
//
//        global.setFlag(false);
//
//        super.onBackPressed();
//        //  Global global = new Global();
//
//        //    this.finish();
//    }
//
//
//    @Override
//    protected void onStop() {
//
//        global.setFlag(true);
//        Log.i(TAG, "onStop: stoppig activity"+global
//                .getFlag());
//        //this.finish();
//            onBackPressed();
//            super.onStop();
//
//
//
//    }


    @Override
    protected void onStop() {
        global.setFlag(false);
        Log.i(TAG, "onStop: flag value "+global.getFlag());
            Intent Timer = new Intent(context, Timer.class);

            context.startService(Timer);


        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(killer);
    }

    void displayAlert()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Time to Protect you Eyes froms Screens");
        // alert.setMessage(th"Message");
     //   alert.setCancelable(false);
        this.setFinishOnTouchOutside(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setMessage("You have used Device For 20 mins Now avoid Screens for only 20 seconds");
        alert.setPositiveButton("Turn screen Off", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               // Toast.makeText(Alert.this, "turning off screen", Toast.LENGTH_SHORT).show();
                Intent dialogIntent = new Intent(getApplicationContext(), Count.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
                finish();
//               PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//
//                PowerManager.WakeLock wl = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Your Tag");
//                wl.acquire();
//                wl.release();

                //WindowManager.LayoutParams params = getWindow().getAttributes();
                //params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                //params.screenBrightness = 0;
                //getWindow().setAttributes(params);

                //onBackPressed();



            }
        });

        alert.setNegativeButton("Wait",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                      //  Intent i = new Intent(getApplicationContext(), Timer.class);
                        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //   context.startActivity(i);
                        //startService(i);
                        Log.i(TAG, "onReceive: service called ");



                        onBackPressed();

                    }
                });
        alert.setCancelable(false);
        Log.i(TAG, "displayAlert:  show");


        alert.show();


    }

}
