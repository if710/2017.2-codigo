package br.ufpe.cin.if710.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BateriaActivity extends Activity {
    private ProgressBar barra_carga=null;
    private TextView status_carga=null;
    private TextView nivel_carga=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bateria);
        barra_carga = (ProgressBar) findViewById(R.id.barra_carga);
        status_carga = (TextView) findViewById(R.id.status_carga);
        nivel_carga = (TextView) findViewById(R.id.nivel_carga);
    }

    BroadcastReceiver onBattery=new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int pct=
                    100 * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 1)
                            / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);

            barra_carga.setProgress(pct);
            nivel_carga.setText(String.valueOf(pct) + "%");


            switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    status_carga.setText("carregando");
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

                    if (plugged == BatteryManager.BATTERY_PLUGGED_AC
                            || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                        status_carga.setText("bateria cheia, plugada");
                    }
                    else {
                        status_carga.setText("bateria cheia, mas descarregando");
                    }
                    break;

                default:
                    status_carga.setText("bateria descarregando");
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter f=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(onBattery, f);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(onBattery);
    }
}
