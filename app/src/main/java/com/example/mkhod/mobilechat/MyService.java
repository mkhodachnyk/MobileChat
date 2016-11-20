package com.example.mkhod.mobilechat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.mkhod.mobilechat.activities.MainActivity;
import com.example.mkhod.mobilechat.models.Message;
import com.example.mkhod.mobilechat.models.OnMessageSentEvent;
import com.example.mkhod.mobilechat.models.UserLab;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkhod on 18.11.2016.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";
    public static final String SECONDS_INTERVAL_EXTRA = "interval_extra";
    public static final String EXTRA_MESSAGE_INFO = "extra_message_info";

    NotificationCompat.Builder builder;
    NotificationManager notificationManager;

    private int secondsInterval;
    private int i = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand(...)");
        secondsInterval = intent.getIntExtra(SECONDS_INTERVAL_EXTRA, 3);
        sendMessages(secondsInterval);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    void sendMessages(final int interval) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    Log.d(TAG, "i = " + i++);
                    try {
                        Message message = new Message("New message " + i, false);
                        UserLab.getInstance(getApplicationContext())
                                .getUsers()
                                .get(1)
                                .addMessage(message);
                        UserLab.getInstance(getApplicationContext())
                                .getUsers()
                                .get(3)
                                .addMessage(message);
                        EventBus.getDefault().post(new OnMessageSentEvent(true));
                        TimeUnit.SECONDS.sleep(PrefUtils.getInerval(MyService.this));

                        builder = new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(R.drawable.ic_sms_black_24dp)
                                .setContentTitle("New message")
                                .setContentText(message.getText());

                        Intent resultIntent = new Intent(MyService.this, MainActivity.class);

                        UUID userId = UserLab.getInstance(MyService.this).getUsers().get(3).getId();
                        resultIntent.putExtra(EXTRA_MESSAGE_INFO, userId);

                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                        stackBuilder.addParentStack(MainActivity.class);

                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(resultPendingIntent);

                        notificationManager.notify(0, builder.build());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind(Intent intent)");
        return null;
    }


}
