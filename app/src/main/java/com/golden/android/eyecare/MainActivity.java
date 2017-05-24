package com.golden.android.eyecare;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.entity.Library;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;
import com.mikepenz.aboutlibraries.ui.item.LibraryItem;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {
    public static final String FRAGTAG = "RepeatingAlarmFragment";




    /**
     *Permission permission code of flow to display customized FloatingView
     */
    private static final int CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101;

    String TAG ="MainActivity";
    ScreenReceiver screenReceiver =new ScreenReceiver();

    @Override
    protected void onPostResume() {
        updateScore();



        super.onPostResume();
    }


    @Override
    protected void onResume() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Boolean Toggle = sharedPreferences.getBoolean("example_switch",true);
        Intent intent2 = new Intent(getApplicationContext(), MyAlarmReceiver.class);

//        final PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234,
//                intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        boolean running =(PendingIntent.getBroadcast(getApplicationContext(), 1234,
//                intent2, PendingIntent.FLAG_UPDATE_CURRENT)!=null);

        boolean isWorking = (PendingIntent.getBroadcast(getApplicationContext(), 1234, intent2, PendingIntent.FLAG_NO_CREATE) != null);

        if(Toggle&&!isWorking)
        {
            Intent Timer = new Intent(getApplicationContext(), Timer.class);

            startService(Timer);
        }




        final Button turnOnButton = (Button) findViewById(R.id.turnon);
        turnOnButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                sharedPreferences.edit().putBoolean("example_switch",true).apply();
                Toast.makeText(MainActivity.this, "Turned On", Toast.LENGTH_SHORT).show();
                turnOnButton.setVisibility(View.GONE);

            }
        });

        if (!Toggle)
        {
            turnOnButton.setVisibility(View.VISIBLE); //SHOW the button

        }



        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        startActivity(intent);

        FirstStart();
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);


        boolean permissionCheck = Settings.canDrawOverlays(getApplicationContext());
        if(!permissionCheck) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivityForResult(intent, CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE);
        }
//        Intent dialogIntent = new Intent(getApplicationContext(), CustomFloatingViewService.class);
//        //dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startService(dialogIntent);


       // registerReceiver(screenReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));
        android.util.Log.i(TAG, "onCreate: android");
        //updateScore();
        //scheduleAlarm();
     //   callAlarmFragment();








    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if(false)
//       Toast.makeText(MainActivity.this, "unregistering Receiver", Toast.LENGTH_SHORT).show();
//            unregisterReceiver(screenReceiver);
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        //  loadHeadersFromResource(R.xml.pref_headers, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                //Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case R.id.item2:
                //Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();

                Intent guide = new Intent(getApplicationContext(),IntroActivity.class);
                guide.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.addFlags(I)

                startActivity(guide);
                return true;
            case R.id.item3:
                //Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                about();

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    void about()
    {


        LibsBuilder l=new LibsBuilder();

        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
        l.withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR);
        l.withAutoDetect(true);
        l.withLibraries("aaa");
        //start the activity
        l.withAboutAppName(getString(R.string.app_name));
        l.withAboutIconShown(true);
        l.withAboutVersionShown(true);
        l.withAboutDescription("This is a app that helps you to protect your eyes from too much use of smartphones.<br /><b>App is designed by Pratik Khadtale</b>");
        l.start(this);


    }

    void setup(){
        SharedPreferences shared = getSharedPreferences("score", Context.MODE_PRIVATE);
       // String myScore = shared.getString("MyScore", null);
       // TextView Sc = (TextView) findViewById(R.id.textView3);
        //Integer i = Integer.parseInt(myScore);
       Integer i=20;
      //example change
        shared.edit().putInt("MyScore",i).apply();















        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString("example_list","20").apply();
    }


    public void scheduleAlarm() {
        final int FIFTEEN_SEC_MILLIS = 120;
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HALF_HOUR, pIntent);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                FIFTEEN_SEC_MILLIS, pIntent);
    }

    public void callAlarmFragment() {

        if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null ) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RepeatingAlarmFragment fragment = new RepeatingAlarmFragment();
            transaction.add(fragment, FRAGTAG);
            transaction.commit();
        }


    }

    void updateScore()
    {

        SharedPreferences shared = getSharedPreferences("score", Context.MODE_PRIVATE);
        Integer myScore = shared.getInt("MyScore",0);
        TextView Sc = (TextView) findViewById(R.id.textView);
      //  Integer i =Integer.parseInt(myScore);
       // i+=20;

       // myScore=i.toString();
        Sc.setText(""+myScore); //do not remove "" please -Pratik
    }
    void FirstStart(){

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                String s=getPrefs.getString("example_text",null);
                //  If the activity has never started before...
                if (s==null) {

               //  Toast.makeText(MainActivity.this, "Fist run", Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags(I)

                    startActivity(intent);

                    Intent Timer = new Intent(getApplicationContext(), Timer.class);

                    startService(Timer);
                    //  Launch app intro
                    //Intent i = new Intent(MainActivity.this,DefaultIntro.class);
//                    //startActivity(i);
//                    Intent intent= new Intent(getApplicationContext(),Timer.class);
//                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startService(intent);
//
//                    registerReceiver(screenReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false).commit();
                    setup();

                    //  Apply changes
                   // e.apply();
                }
            }
        });

        // Start the thread
        t.start();



    }

}
