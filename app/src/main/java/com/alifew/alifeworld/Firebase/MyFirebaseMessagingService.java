package com.alifew.alifeworld.Firebase;

import static com.alifew.alifeworld.Utils.Constants.NOTIFICATION_CHANNEL;
import static com.alifew.alifeworld.Utils.Constants.NOTIFICATION_DESCRIPTION;
import static com.alifew.alifeworld.Utils.Constants.NOTIFICATION_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.alifew.alifeworld.API.ApiUtilize;
import com.alifew.alifeworld.API.Retrofit_client;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.TokenUpdateApi;
import com.alifew.alifeworld.session.SessionManagement;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.saveDeviceToken(token);

        int userId =sessionManagement.getUserID();
        String type = sessionManagement.getType();

        if (userId != 0 && type != null) {
            sendTokenToServer(String.valueOf(userId), token, type);
        }
    }

 @Override
 public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
     super.onMessageReceived(remoteMessage);
     Log.d("dataxx", "onMessageReceived: "+remoteMessage);
     // Check if the message contains a notification payload
     if (remoteMessage.getNotification() != null) {
         String title = remoteMessage.getNotification().getTitle();
         String message = remoteMessage.getNotification().getBody();

         showNotification(title, message);
     }
 }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = NOTIFICATION_ID;

        // Create Notification Channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(NOTIFICATION_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }

        // Build Notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.bell_icon) // Replace with your app's notification icon
                        .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendTokenToServer(String id, String token, String type) {
        TokenUpdateApi api = Retrofit_client
                .getClient(ApiUtilize.BASE_URL) // ✅ Replace with your actual API URL
                .create(TokenUpdateApi.class);

        Call<CommonResponse> call = api.newTokenUpdate(id, token, type);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("dataxx", "Token updated successfully: " + response.body().message);
                } else {
                    Log.e("dataxx", "Token update failed: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                Log.e("dataxx", "Error sending token: " + t.getMessage());
            }
        });
    }
}
