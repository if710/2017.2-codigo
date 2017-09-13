package br.ufpe.cin.if710.managers.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmLogReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("IF710","Alarme registrado em:" + DateFormat.getDateTimeInstance().format(new Date()));
    }
}
