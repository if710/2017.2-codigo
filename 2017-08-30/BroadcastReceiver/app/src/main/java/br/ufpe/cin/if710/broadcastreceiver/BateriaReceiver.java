package br.ufpe.cin.if710.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BateriaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
            Toast.makeText(context, "Bateria baixa", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Bateria OK", Toast.LENGTH_SHORT).show();
        }
    }
}
