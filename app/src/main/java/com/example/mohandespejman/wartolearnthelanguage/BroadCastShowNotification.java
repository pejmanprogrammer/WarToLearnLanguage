package com.example.mohandespejman.wartolearnthelanguage;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by Pejman on 4/4/2018.
 */

public class BroadCastShowNotification extends BroadcastReceiver {

    SharedPreferences sharedPreferences ;
    int numshow=0;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = context.getSharedPreferences("insidedata" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkprice" , true);
        editor.apply();

        if(sharedPreferences.contains("numshow"))
        {

            numshow = sharedPreferences.getInt("numshow" , 0);
            numshow++;

            if(numshow <= 8)
            {

                editor.putInt("numshow" , numshow);
                editor.apply();
            }




        }



        if(numshow <= 7)
        {


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.coin)
                            .setContentTitle("متن عنوان")
                            .setContentText("متن توضیحات");

            Intent resultIntent = new Intent(context, ActivitySelectHero.class);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

            stackBuilder.addParentStack(ActivitySelectHero.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            mNotificationManager.notify(1 , notification);


        }






    }
}
