package com.duarbd.duarhcentralhub.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.duarbd.duarhcentralhub.R;

public class NotificationHelper {
    public static final String CHANNEL_ID ="duar_centralhub_id";
    public static final String CHANNEL_NAME="duar_centralhub_channel";
    public static final String CHANNEL_DES="We are Duar-Team";

    public static void displayNotification(Context context, String nTitle, String nBody,String data){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DES);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(nTitle)
                .setContentText(nBody)
                .setSound(Utils.getDefaultNotificationToneUri())
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1,notificationBuilder.build());//id is used to delete or modify this notification

    }
}