package com.duarbd.duarhcentralhub.tools;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;

public class App extends Application {
    private static App instance;
    public  final String CHANNEL_ID ="duar_centralhub_id";
    public  final String CHANNEL_NAME="duar_hub_channel";

    public static App getInstance() {
        return instance;
    }

    public Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance=this;
        super.onCreate();

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),attributes);
            NotificationManager notificationManager=getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
