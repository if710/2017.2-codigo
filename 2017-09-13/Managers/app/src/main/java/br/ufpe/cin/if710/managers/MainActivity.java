package br.ufpe.cin.if710.managers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.ufpe.cin.if710.managers.alarm.AlarmManagerActivity;
import br.ufpe.cin.if710.managers.jobscheduler.JobSchedulerActivity;
import br.ufpe.cin.if710.managers.location.LocationMapsActivity;
import br.ufpe.cin.if710.managers.notification.NotificationManagerActivity;
import br.ufpe.cin.if710.managers.phonesms.PhoneSmsActivity;
import br.ufpe.cin.if710.managers.pkg.PackageManagerActivity;
import br.ufpe.cin.if710.managers.power.PowerManagerActivity;
import br.ufpe.cin.if710.managers.sensor.SensorManagerActivity;

public class MainActivity extends AppCompatActivity {

    public static final String PACKAGE_NAME = "br.ufpe.cin.if710.managers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationManagerActivity.class));
            }
        });

        Button b2 = (Button) findViewById(R.id.btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AlarmManagerActivity.class));
            }
        });

        Button b3 = (Button) findViewById(R.id.btn3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JobSchedulerActivity.class));
            }
        });

        Button b4 = (Button) findViewById(R.id.btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PowerManagerActivity.class));

            }
        });
        Button b5 = (Button) findViewById(R.id.btn5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SensorManagerActivity.class));

            }
        });
        Button b6 = (Button) findViewById(R.id.btn6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PackageManagerActivity.class));

            }
        });
        Button b7 = (Button) findViewById(R.id.btn7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PhoneSmsActivity.class));

            }
        });
        Button b8 = (Button) findViewById(R.id.btn8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LocationMapsActivity.class));

            }
        });
    }
}
