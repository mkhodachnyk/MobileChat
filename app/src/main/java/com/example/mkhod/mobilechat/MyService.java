package com.example.mkhod.mobilechat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.mkhod.mobilechat.models.Message;
import com.example.mkhod.mobilechat.models.OnMessageSentEvent;
import com.example.mkhod.mobilechat.models.UserLab;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

/**
 * Created by mkhod on 18.11.2016.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";
    public static final String SECONDS_INTERVAL_EXTRA = "interval_extra";

    private int secondsInterval;
    private int i = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
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
                        UserLab.getInstance(getApplicationContext())
                                .getUsers()
                                .get(1)
                                .addMessage(new Message("New message " + i, false));
                        UserLab.getInstance(getApplicationContext())
                                .getUsers()
                                .get(3)
                                .addMessage(new Message("New message " + i, false));
                        EventBus.getDefault().post(new OnMessageSentEvent(true));
                        TimeUnit.SECONDS.sleep(interval);
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
