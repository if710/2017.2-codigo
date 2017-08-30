package br.ufpe.cin.if710.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class DynamicReceiver extends BroadcastReceiver {

    private final String TAG = "DynamicReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "INTENT Recebido");
        Toast.makeText(context, "INTENT Recebido pelo DynamicReceiver", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }

}
