package com.sownt.awesomeclass.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.sownt.awesomeclass.App;
import com.sownt.awesomeclass.R;

import java.util.Date;

public class NotificationPublisher extends BroadcastReceiver {
    public static final String NOTIFICATION_CONTENT = "NOTIFICATION_CONTENT";
    public static final String NOTIFICATION_LINK = "NOTIFICATION_LINK";

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = intent.getStringExtra(NOTIFICATION_CONTENT);
        String link = intent.getStringExtra(NOTIFICATION_LINK);
        Uri uri = Uri.parse("https://classroom.google.com/c/MzIwODg0MjcxMDQx");

        try {
            if (!link.isEmpty()) uri = Uri.parse(link);
        } catch (Exception e) {
            Log.e("AwesomeClass", e.getMessage());
        }
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, App.NOTIFICATION_ID)
                .setContentTitle(context.getString(R.string.upcommint_lecture))
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_round_notifications_active_24)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.clip_welcome))
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) new Date().getTime(), notification);
    }
}
