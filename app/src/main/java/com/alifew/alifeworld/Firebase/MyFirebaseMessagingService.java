package com.alifew.alifeworld.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.alifew.alifeworld.API.ApiUtilize;
import com.alifew.alifeworld.API.Retrofit_client;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.Constants;
import com.alifew.alifeworld.model.CommonResponse;
import com.alifew.alifeworld.model.TokenUpdateApi;
import com.alifew.alifeworld.session.SessionManagement;
import com.alifew.alifeworld.view.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        //need to do work here
        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        sessionManagement.saveDeviceToken(token);

        int userId =sessionManagement.getUserID();
        String type = sessionManagement.getType();
/*
        HashMap map = new HashMap();
        map.put("ID", userId);
        map.put("TYPE", type);
        map.put("TOKEN", token);
        Log.d("dataxx", String.valueOf(map));*/

        if (userId != 0 && type != null) {
            sendTokenToServer(String.valueOf(userId), token, type);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String channelId = Constants.NOTIFICATION_ID;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, Constants.NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }



    private void sendTokenToServer(String id, String token, String type) {
        TokenUpdateApi api = Retrofit_client
                .getClient(ApiUtilize.BASE_URL) // ✅ Replace with your actual API URL
                .create(TokenUpdateApi.class);

        Call<CommonResponse> call = api.newTokenUpdate(id, token, type);
        call.enqueue(new Callback<CommonResponse>() {
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
