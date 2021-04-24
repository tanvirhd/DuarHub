package com.duarbd.duarhcentralhub.services;

import androidx.annotation.NonNull;

import com.duarbd.duarhcentralhub.tools.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage!=null){
            String title=remoteMessage.getNotification().getTitle();
            String message_body=remoteMessage.getNotification().getBody();
            Map<String,String> fcm_data=remoteMessage.getData();
            NotificationHelper.displayNotification(getApplicationContext(),title,message_body,fcm_data.get("delivery_request_id"));
        }
    }
}
