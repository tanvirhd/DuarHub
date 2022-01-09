package com.duarbd.duarhcentralhub.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.presenter.ActivityHome;
import com.duarbd.duarhcentralhub.tools.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "CustomFirebaseMessaging";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: fcm notification received successfully");

        if(remoteMessage!=null){

            String title=remoteMessage.getNotification().getTitle();
            String message_body=remoteMessage.getNotification().getBody();
            Log.d(TAG, "onMessageReceived: called title:"+title+" body:"+message_body);
            //Map<String,String> fcm_data=remoteMessage.getData();

            //todo notification id is needed
            NotificationHelper notificationHelper=new NotificationHelper(getApplicationContext(),1);
            notificationHelper.displayNotification(getApplicationContext(),title,message_body);
        }
    }

    int codeStringToInteger(String code){
        int finalCode=0;
        for(int i=0;i<code.length();i++){
            Log.d(TAG, "codeStringToInteger: char="+code.charAt(i)+" value="+Character.getNumericValue(code.charAt(i)));
            finalCode=finalCode+Character.getNumericValue(code.charAt(i));
        }
        return finalCode;
    }
}
