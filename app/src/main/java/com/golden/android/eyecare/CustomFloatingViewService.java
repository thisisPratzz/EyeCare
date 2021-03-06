package com.golden.android.eyecare;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
//import android.support.v4.app.NotificationCompat;

import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

/**
 * FloatingViewのカスタマイズを行います。
 * サンプルとしてクリック時にはメールアプリを起動します。
 */
public class CustomFloatingViewService extends Service implements FloatingViewListener {


    Context context;
    String TAG = "FloatingView";
    Global global;


    /**
     * 通知ID
     */
    private static final int NOTIFICATION_ID = 908114;

    /**
     * Prefs Key(Last position X)
     */
    private static final String PREF_KEY_LAST_POSITION_X = "last_position_x";

    /**
     * Prefs Key(Last position Y)
     */
    private static final String PREF_KEY_LAST_POSITION_Y = "last_position_y";

    /**
     * FloatingViewManager
     */
    private FloatingViewManager mFloatingViewManager;

    @Override
    public void onCreate() {
        super.onCreate();


        context = getApplicationContext();
        global = (Global) getApplicationContext();





        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        final BroadcastReceiver screenoffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.v("screenoffReceiver", "SCREEN OFF");
                    onFinishFloatingView();
                    //onDestroy();
                    stopForeground(Boolean.TRUE);
                }
                return;
            }
        };
        registerReceiver(screenoffReceiver, filter);







    }

    /**
     * {@inheritDoc}
     */


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 既にManagerが存在していたら何もしない
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final LayoutInflater inflater = LayoutInflater.from(this);
        Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
//        if(intent.getExtras()==null)
//        {
//
//        }
//        else {
//            Boolean data = (Boolean) intent.getExtras().getBoolean("noticlick");
//
//            if (data) {
//
//                launchCount();
//                //Do your stuff here mate :)
//            }
//        }




        final ImageView iconView = (ImageView) inflater.inflate(R.layout.floating, null, false);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // メールアプリの起動
//                final Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.mail_address), null));
//                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_title));
//                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_content));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                launchCount();


            }
        });

        String ToastString = getString(R.string.Toasttext1) + " " + checkTime() + " " + getString(R.string.Toasttext2);
        Toast toast = Toast.makeText(getApplicationContext(), ToastString, Toast.LENGTH_SHORT);
//      Set the Gravity to Top and Left
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 100, 200);
        toast.setDuration(Toast.LENGTH_LONG);

        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(20);
        toast.show();


        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        // Setting Options(you can change options at any time)
        loadDynamicOptions();
        // Initial Setting Options (you can't change options after created.)
        final FloatingViewManager.Options options = loadOptions(metrics);
        mFloatingViewManager.addViewToWindow(iconView, options);
        mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        // 常駐起動
        //startForeground(NOTIFICATION_ID, createNotification(intent));
        notify(intent);
      //  return START_REDELIVER_INTENT;
        return START_STICKY;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        destroy();


        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFinishFloatingView() {


        stopSelf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (!isFinishing) {
            // Save the last position
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt(PREF_KEY_LAST_POSITION_X, x);
            editor.putInt(PREF_KEY_LAST_POSITION_Y, y);
            editor.apply();
        }
    }

    /**
     * Viewを破棄します。
     */
    private void destroy() {
        global.setFlag(false);
        Log.i(TAG, "onStop: flag value " + global.getFlag());
        Intent Timer = new Intent(context, Timer.class);

        context.startService(Timer);

        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }

    }

    /**
     * It displays the notification.
     */
    private void createNotification(Intent intent) {



//        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        if (intent.getAction()=="from checker")
//        {
//        builder.setWhen(System.currentTimeMillis());
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentTitle(getString(R.string.app_name));
//        builder.setContentText(getString(R.string.notification_content_text));
//        builder.setOngoing(true);
//        builder.setPriority(NotificationCompat.PRIORITY_MIN);
//        builder.setDefaults(Notification.DEFAULT_SOUND);
//       // builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
//        // PendingIntent作成
////        final Intent notifyIntent = new Intent(this, DeleteActionActivity.class);
////        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        builder.setContentIntent(notifyPendingIntent);
////
//        //noticlick
//        Intent myIntent = new Intent(context, CustomFloatingViewService.class);
//        myIntent.putExtra("noticlick",true);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                CustomFloatingViewService.this,
//                0,
//                myIntent,Intent.FILL_IN_ACTION);
//
//
//
//        builder.setContentIntent(pendingIntent);
//        }
//        return builder.build();
    }

    /**
     * 動的に変更可能なオプションを読み込みます。
     */
    private void loadDynamicOptions() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        final String displayModeSettings = sharedPref.getString("settings_display_mode", "");
        if ("Always".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        } else if ("FullScreen".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_FULLSCREEN);
        } else if ("Hide".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_ALWAYS);
        }

    }

    /**
     * FloatingViewのオプションを読み込みます。
     *
     * @param metrics X/Y座標の設定に利用するDisplayMetrics
     * @return Options
     */
    private FloatingViewManager.Options loadOptions(DisplayMetrics metrics) {
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Shape
        final String shapeSettings = sharedPref.getString("settings_shape", "");
        if ("Circle".equals(shapeSettings)) {
            options.shape = FloatingViewManager.SHAPE_CIRCLE;
        } else if ("Rectangle".equals(shapeSettings)) {
            options.shape = FloatingViewManager.SHAPE_RECTANGLE;
        }

        // Margin
        final String marginSettings = sharedPref.getString("settings_margin", String.valueOf(options.overMargin));
        options.overMargin = Integer.parseInt(marginSettings);

        // MoveDirection
        final String moveDirectionSettings = sharedPref.getString("settings_move_direction", "");
        if ("Default".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_DEFAULT;
        } else if ("Left".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_LEFT;
        } else if ("Right".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_RIGHT;
        } else if ("Fix".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NONE;
        }

        // Last position
        final boolean isUseLastPosition = sharedPref.getBoolean("settings_save_last_position", false);
        if (isUseLastPosition) {
            final int defaultX = options.floatingViewX;
            final int defaultY = options.floatingViewY;
            options.floatingViewX = sharedPref.getInt(PREF_KEY_LAST_POSITION_X, defaultX);
            options.floatingViewY = sharedPref.getInt(PREF_KEY_LAST_POSITION_Y, defaultY);
        } else {
            // Init X/Y
            final String initXSettings = sharedPref.getString("settings_init_x", "");
            final String initYSettings = sharedPref.getString("settings_init_y", "");
            if (!TextUtils.isEmpty(initXSettings) && !TextUtils.isEmpty(initYSettings)) {
                final int offset = (int) (48 + 8 * metrics.density);
                options.floatingViewX = (int) (metrics.widthPixels * Float.parseFloat(initXSettings) - offset);
                options.floatingViewY = (int) (metrics.heightPixels * Float.parseFloat(initYSettings) - offset);
            }
        }

        // Initial Animation
        final boolean animationSettings = sharedPref.getBoolean("settings_animation", options.animateInitialMove);
        options.animateInitialMove = animationSettings;

        return options;
    }


    int checkTime() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String s = sharedPreferences.getString("example_list", "20");
        //getString("example_list","20");

        Integer i = Integer.parseInt(s);
        return i;


    }


void launchCount()
{

    Intent dialogIntent = new Intent(getApplicationContext(), Count.class);
    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    startActivity(dialogIntent);
    stopForeground(true );
    stopSelf();

}


void notify(Intent intent)
{
//    Boolean b=intent.getExtras().getBoolean("fromchecker");
        String a =intent.getAction();
    //if(intent.getExtras().getBoolean("fromchecker"))
   // {
//        Intent myIntent = new Intent(this, Count.class);
//        myIntent.putExtra("remove",true);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0,myIntent,0);
        final Intent notifyIntent = new Intent(this, Count.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Intent newIntent = new Intent(context,Count.class);
//        pendingIntent = PendingIntent.getService(this, 0,newIntent,0);

    final Intent remove = new Intent(this, RemoveNotification.class);
    PendingIntent removePendingIntent = PendingIntent.getService(this, 0, remove, PendingIntent.FLAG_UPDATE_CURRENT);


    MediaPlayer ring= MediaPlayer.create(getApplicationContext(),R.raw.notifyring);
    ring.start();

//
//        Intent playIntent = new Intent(this, CustomFloatingViewService.class);
//        playIntent.setAction("remove");
//        PendingIntent pplayIntent = PendingIntent.getService(this, 0, playIntent, 0);


        Notification notification =  new NotificationCompat.Builder(this)
                .setOngoing(true)
                .setStyle(new NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(
                                new int[]{0}))  // show only play/pause in compact view)
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.notification_content_text))
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_MIN)
        .setDefaults(Notification.DEFAULT_SOUND)
        .setCategory(NotificationCompat.CATEGORY_SERVICE)

        .setContentIntent(notifyPendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifyring))
                .addAction(android.R.drawable.ic_delete,"Remove notification",removePendingIntent)
                //                .addAction(android.R.drawable.ic_media_previous,
//
//                  "Previous", pplayIntent)
                .build();


        startForeground(NOTIFICATION_ID, notification);

 //   }
//    else// if (intent.getExtras().getBoolean("remove"))
//    {
//        launchCount();
//    }


}



}