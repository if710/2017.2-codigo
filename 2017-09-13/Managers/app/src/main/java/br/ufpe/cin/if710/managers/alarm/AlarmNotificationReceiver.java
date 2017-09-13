package br.ufpe.cin.if710.managers.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import br.ufpe.cin.if710.managers.MainActivity;
import br.ufpe.cin.if710.managers.R;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    // ID --- permite atualizar a notificação
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String TAG = "NotificationReceiver";

    // Elementos a compor Notification
    private final CharSequence tickerText = "Mensagem de texto que aparece na barra superior do telefone";
    private final CharSequence contentTitle = "Algum lembrete";
    private final CharSequence contentText = "Alguma mensagem...";

    // Ações da Notification
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    // Som e padrão de vibração
    private final Uri soundURI = Uri.parse("android.resource://"+ MainActivity.PACKAGE_NAME+"/" + R.raw.alarm_rooster);
    private final long[] mVibratePattern = { 0, 200, 200, 300 };


    @Override
    public void onReceive(Context context, Intent intent) {
        // Intent a ser usado quando usuário clicar na Notification
        mNotificationIntent = new Intent(context, MainActivity.class);

        // Objeto PendingIntent que envolve o Intent
        mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, 0);

        // Cria Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setTicker(tickerText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent)
                .setSound(soundURI).setVibrate(mVibratePattern);

        // Pega NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Passa Notification para o NotificationManager
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());

        // Faz o Log da chamada ao método notify()
        Log.i(TAG, "Enviando notification em:" + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
