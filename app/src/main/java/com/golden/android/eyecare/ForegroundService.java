/*
 * Copyright (c) 2016. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.golden.android.eyecare;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
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

public class ForegroundService extends Service implements FloatingViewListener {
    private static final String LOG_TAG = "ForegroundService";

    Context context;
    String TAG = "FloatingView";
    Global global;
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
                    onDestroy();
                    stopForeground(Boolean.TRUE);
                }
                return;
            }
        };
        registerReceiver(screenoffReceiver, filter);









    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        //Do nothing If you already exist Manager
        if (mFloatingViewManager != null) {
            return START_REDELIVER_INTENT;
        }
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final LayoutInflater inflater = LayoutInflater.from(this);
//        Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//        // Vibrate for 500 milliseconds
//        v.vibrate(1000);
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

//        String ToastString = getString(R.string.Toasttext1) + " " + checkTime() + " " + getString(R.string.Toasttext2);
//        Toast toast = Toast.makeText(getApplicationContext(), ToastString, Toast.LENGTH_SHORT);
////      Set the Gravity to Top and Left
//        toast.setGravity(Gravity.TOP | Gravity.LEFT, 100, 200);
//        toast.setDuration(Toast.LENGTH_LONG);
//
//        ViewGroup group = (ViewGroup) toast.getView();
//        TextView messageTextView = (TextView) group.getChildAt(0);
//        messageTextView.setTextSize(20);
//        toast.show();


        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        // Setting Options(you can change options at any time)
        loadDynamicOptions();
        // Initial Setting Options (you can't change options after created.)
        final FloatingViewManager.Options options = loadOptions(metrics);
        mFloatingViewManager.addViewToWindow(iconView, options);
        mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);

        notify(intent);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();

        super.onDestroy();

        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    @Override
    public void onFinishFloatingView() {


        stopSelf();
    }

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
        options.animateInitialMove = Boolean.TRUE;
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

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");

//
//            Intent notificationIntent = new Intent(this, MainActivity.class);
//            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
//            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                    notificationIntent, 0);

            Intent notificationIntent = new Intent(this, ForegroundService.class);
            notificationIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                    notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            Intent previousIntent = new Intent(this, ForegroundService.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, ForegroundService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_eye);
            Intent dialogIntent = new Intent(getApplicationContext(), Count.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
          //  startActivity(dialogIntent);
            PendingIntent pplayIntent1 = PendingIntent.getService(this, 0,
                    dialogIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Truiton Music Player");
            builder.setTicker("Truiton Music Player");
            builder.setContentText("My Music");
            builder.setSmallIcon(R.drawable.ic_eye);
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(false);
            Notification notification = builder.build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
                launchCount();
        }
    }

}
