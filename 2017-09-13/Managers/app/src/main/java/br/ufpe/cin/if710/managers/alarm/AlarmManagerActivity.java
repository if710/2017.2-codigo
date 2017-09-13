package br.ufpe.cin.if710.managers.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufpe.cin.if710.managers.R;

public class AlarmManagerActivity extends AppCompatActivity {
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent, mLoggingReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent,
            mLoggingReceiverPendingIntent;
    private static final long INITIAL_ALARM_DELAY = 1000L;
    protected static final long JITTER = 5000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);

        // Botao para abrir activity de alarme simples - repetido
        final Button simpleAlarmButton = (Button) findViewById(R.id.simple_alarm_button);
        simpleAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AlarmSimpleActivity.class));
            }
        });


        // Pegando o servico
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //mAlarmManager = getSystemService(AlarmManager.class);

        // Criando Intent para enviar broadcast para o NotificationReceiver
        mNotificationReceiverIntent = new Intent(this, AlarmNotificationReceiver.class);

        // Criando PendingIntent que guarda o mNotificationReceiverIntent - metodo getBroadcast
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mNotificationReceiverIntent, 0);

        // Criando Intent para enviar broadcast para o LoggingReceiver
        mLoggingReceiverIntent = new Intent(this, AlarmLogReceiver.class);

        // Criando PendingIntent que guarda o mLoggingReceiverIntent
        mLoggingReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mLoggingReceiverIntent, 0);

        // Botao para definir single alarm Button
        final Button singleAlarmButton = (Button) findViewById(R.id.single_alarm_button);
        singleAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Definindo single alarm com RTC_WAKEUP
                // (nao eh o mais indicado neste caso, mas apenas para exercicio)
                mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + INITIAL_ALARM_DELAY,
                        mNotificationReceiverPendingIntent);

                // Define outro single alarm que dispara logo apos o anterior
                //inexact por default...
                mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + INITIAL_ALARM_DELAY
                                + JITTER, mLoggingReceiverPendingIntent);



                // Mostra Toast
                Toast.makeText(getApplicationContext(), "One-shot alarm definido",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Botao para definir repeating alarm
        final Button repeatingAlarmButton = (Button) findViewById(R.id.repeating_alarm_button);
        repeatingAlarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Define repeating alarm, usando ELAPSED_REALTIME
                //nao tem comportamento diferente de setInexactRepeating a partir de Android 4.4+ API 19+
                mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mNotificationReceiverPendingIntent);

                // Define outro repeating alarm que dispara logo apos o anterior
                mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
                                + JITTER,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mLoggingReceiverPendingIntent);

                // Mostra Toast
                Toast.makeText(getApplicationContext(), "Repeating alarm definido",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Botao para definir inexact repeating alarm
        final Button inexactRepeatingAlarmButton = (Button) findViewById(R.id.inexact_repeating_alarm_button);
        inexactRepeatingAlarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Define inexact repeating alarm, usando ELAPSED_REALTIME
                mAlarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mNotificationReceiverPendingIntent);

                // Define outro inexact repeating alarm que dispara logo apos o anterior
                mAlarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
                                + JITTER,
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        mLoggingReceiverPendingIntent);

                // Mostra Toast
                Toast.makeText(getApplicationContext(),
                        "Inexact Repeating Alarm definido", Toast.LENGTH_LONG)
                        .show();
            }
        });

        // Botao para cancelar repeating alarm
        final Button cancelRepeatingAlarmButton = (Button) findViewById(R.id.cancel_repeating_alarm_button);
        cancelRepeatingAlarmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Cancela todos os alarmes usando mNotificationReceiverPendingIntent
                mAlarmManager.cancel(mNotificationReceiverPendingIntent);

                // Cancela todos os alarmes usando mLoggingReceiverPendingIntent
                mAlarmManager.cancel(mLoggingReceiverPendingIntent);

                // Mostra Toast
                Toast.makeText(getApplicationContext(),
                        "Repeating Alarms cancelados", Toast.LENGTH_LONG).show();
            }
        });
    }
}
