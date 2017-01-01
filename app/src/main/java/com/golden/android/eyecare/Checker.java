package com.golden.android.eyecare;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import java.util.Timer;

import static android.util.Log.i;

/**
 * Created by Machine on 15-08-2016.
 */
public class Checker extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    String TAG ="Checker";
    public Checker() {
        super("Checker");

    }



    @Override
    protected void onHandleIntent(Intent intent) {
        // protected void onHandleIntent(Intent intent) {
        i("Checker", "onHandleIntent: launched ");
       final   Global global= (Global) getApplicationContext();
        //Global global = null; 
                //= new Global();
        Boolean flag=global.getFlag();
        i(TAG, "onHandleIntent: flag value" + flag);
      // flag=true;
        if (!global.getFlag()) { // to display flag must  be false
            i(TAG, "displayAlert: false so  show");


         //   Toast.makeText(Checker.this, "you will  20 point added to score ", Toast.LENGTH_SHORT).show();

            flag=true;
            global.setFlag(true);
            i(TAG, "onHandleIntent: flag value" + flag);

            Intent dialogIntent = new Intent(this, Alert.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(dialogIntent);


        }
        else
        {
            i(TAG, "displayAlert: display true so kill activity");
         //   Toast.makeText(Checker.this, "Awesome you have 20 point added to score ", Toast.LENGTH_SHORT).show();
            sendBroadcast(new Intent("killerAlert"));
            global.setFlag(false);
            flag=false;

            //this.onBackPressed();
            Intent dialogIntent = new Intent(getApplicationContext(), Timer.class);
            //dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(dialogIntent);


        }



    }


}
