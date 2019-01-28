package com.example.paulo.healthapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.paulo.healthapp.Activity.Splash.SplashLudusActivity;



public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String horario = intent.getStringExtra("horario");
        int qtdTomados = intent.getIntExtra("qtdTomados",0);
        int qtdMedicamento = intent.getIntExtra("qtdMedicamento", 0);
        int id = intent.getIntExtra("id",0);
        createNotification(context, horario, qtdTomados, qtdMedicamento,id);
    }

    public void createNotification(Context context, String horario, int qtdTomados, int qtdMedicamento ,int id){

        PendingIntent notificIntent = PendingIntent.getActivity(context,0, new Intent(context, SplashLudusActivity.class),0);

        Uri alarm_sound = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.sirene);

        RingtoneManager.setActualDefaultRingtoneUri( context, RingtoneManager.TYPE_NOTIFICATION, alarm_sound);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.splash_logo)
                .setContentTitle("Lembrete comprimido" + " " + horario)
                .setTicker("Lembrete")
                .setSound(alarmSound)
                .setContentText("Tomados " + qtdTomados + " de " + qtdMedicamento);

        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id,mBuilder.build());


        //IntentService

    }
}
