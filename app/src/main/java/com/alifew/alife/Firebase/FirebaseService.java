package com.alifew.alife.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.alifew.alife.R;
import com.alifew.alife.view.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);


    }

    private static final String NOTIFICATION_CHANNEL_ID = "Default";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d("dataxx", "onMessageReceived: ");

        String msg = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        notification(msg, title);
    }

    private void notification(String message, String title) {

        if (message != null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
            int requestCode = (int) System.currentTimeMillis();

            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(
                        this, requestCode,
                        intent, PendingIntent.FLAG_MUTABLE
                );
            } else {
                pendingIntent = PendingIntent.getActivity(
                        this, requestCode,
                        intent, PendingIntent.FLAG_IMMUTABLE
                );
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true).setContentIntent(pendingIntent);


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0, builder.build());
        }

    }
}
