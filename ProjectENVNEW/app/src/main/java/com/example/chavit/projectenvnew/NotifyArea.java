package com.example.chavit.projectenvnew;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.Arrays;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Chavit on 1/4/2561.
 */

public class NotifyArea  {
    private static int Notification_ID = 1;
    private static String Notification_Channel_ID = "my_notification_channel";
    private static String VerifyKey= "";

    public static void showNotification(String value, StringBuffer data, Context c) {
        int num = 0;

        String[] noti = data.toString().split(":");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("แจ้งเตือน");

        for (int i = 0; i < noti.length; i++) {

            inboxStyle.addLine(noti[num]);
            num++;

        }
        NotificationManager a = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Notification_Channel_ID, "my_notification_channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            a.createNotificationChannel(notificationChannel);
        }
            if (value.equals("ปลอดภัย")){
                VerifyKey = value;
            }
            else if (!VerifyKey.equals(value)&&!value.equals("ปลอดภัย")) {
                NotificationManager notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(c, Notification_Channel_ID)
                        .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(inboxStyle);
                        //.setContentText(noti[0])
                        //.setAutoCancel(true)
                notificationManager.notify(Notification_ID, builder.build());
                VerifyKey = value;
            }
            Arrays.fill( noti, null );
    }

}
