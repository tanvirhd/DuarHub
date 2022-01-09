package com.duarbd.duarhcentralhub.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.presenter.ActivityHome;

public class NotificationHelper {
    private static final String TAG = "NotificationHelper";
    public  final String CHANNEL_ID ="duar_centralhub_id";
    public int NOTIFICATION_ID;
    public NotificationManager notificationManager;
    public Context context;

    public NotificationHelper(Context context,int notificationId){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
        this.NOTIFICATION_ID=notificationId;
    }

    public void displayNotification(Context context, String nTitle, String nBody){
        Log.d(TAG, "displayNotification: called title:"+nTitle+" body:"+nBody);

            Intent fullScreenIntent = new Intent(context, ActivityHome.class);
            fullScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                    fullScreenIntent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(nTitle)
                    .setContentText(nBody)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setFullScreenIntent(fullScreenPendingIntent, true);

            if(notificationBuilder==null) Log.d(TAG, "displayNotification: null builder");
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    /*public static void displayNotification(Context context, String nTitle, String nBody,String data){

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

    }*/
}