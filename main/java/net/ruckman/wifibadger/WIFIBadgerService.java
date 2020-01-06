package net.ruckman.wifibadger;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class WIFIBadgerService extends Service {
    private boolean isStarted=false;
    private static final int NOTIFICATION_ID = 8111;
    private static final String NOTIFICATION_CHANNEL_ID = "Wifi Badger Service";
    final int SDK_INT = Build.VERSION.SDK_INT;

    //variables
    WIFIBadgerCommonCalls WIFIBadgerCommonCalls = new WIFIBadgerCommonCalls();
    BroadcastReceiver mReceiver = new WIFIBadgerScreenStateReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onCreate();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean screenOn = true;
        if(intent != null) {
            screenOn = intent.getBooleanExtra("screen_state", true);
        } else {
            String WBDATABASE = "WIFIBadgerDB.db";
            SQLiteDatabase db = getApplicationContext().openOrCreateDatabase(WBDATABASE, 0, null);
            Cursor c = db.rawQuery("SELECT * FROM wifibadgersettingstable WHERE rowid='5'", null);
            String screenstate = "ScreenOn";
            if (c.moveToFirst()) {
                screenstate = c.getString(1);
            }
            c.close();
            db.close();
            if (screenstate.equals("ScreenOff")){
                screenOn = false;
            }
        }

        if (!screenOn) {
            WIFIBadgerCommonCalls.SetApplicationScreenState("ScreenOff",getApplicationContext());
            //Start Poller
            start();

        } else {
            WIFIBadgerCommonCalls.SetApplicationScreenState("ScreenOn", getApplicationContext());
            //Start Poller
            start();

        }
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        boolean screenOn = intent.getBooleanExtra("screen_state", true);
        if (!screenOn) {
            WIFIBadgerCommonCalls.SetApplicationScreenState("ScreenOff", getApplicationContext());
            //Start Poller
            start();
        } else {
            WIFIBadgerCommonCalls.SetApplicationScreenState("ScreenOn", getApplicationContext());
            //Start Poller
            start();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void start() {

        isStarted=true;

        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher
        Intent showTaskIntent = new Intent(getApplicationContext(), WIFIBadgerMainActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (SDK_INT < 16) {
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.app_service_running))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .getNotification();
            startForeground(NOTIFICATION_ID, notification);
            //Start Poller
            Intent intent = new Intent(getApplicationContext(),WIFIBadgerWIFIPoller.class);
            startService(intent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.app_name);
                String description = getString(R.string.app_service_running);
                int importance = NotificationManager.IMPORTANCE_MIN;
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle(getString(R.string.app_name_service))
                        .setContentText(getString(R.string.app_service_running))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(contentIntent)
                        .setChannelId(NOTIFICATION_CHANNEL_ID)
                        .build();

                startForeground(NOTIFICATION_ID, notification);
                //Start Poller
                Intent intent = new Intent(getApplicationContext(),WIFIBadgerWIFIPoller.class);
                startService(intent);
        }

        if (SDK_INT > 16 && SDK_INT < 26) {
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name_service))
                    .setContentText(getString(R.string.app_service_running))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .build();
                notification.priority = Notification.PRIORITY_MIN;
            startForeground(NOTIFICATION_ID, notification);
            //Start Poller
            Intent intent = new Intent(getApplicationContext(),WIFIBadgerWIFIPoller.class);
            startService(intent);
        }

    }

    private void stop() {
        if (isStarted) {
            isStarted = false;
            stopForeground(true);
        }
    }

}

