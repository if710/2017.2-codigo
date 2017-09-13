package br.ufpe.cin.if710.managers.power;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.ufpe.cin.if710.managers.R;

public class PowerManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_manager);
        final Button button1 = (Button) findViewById(R.id.btn_keepScreen1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PowerManagerActivity.this, KeepScreenOn1Activity.class));
            }
        });

        final Button button2 = (Button) findViewById(R.id.btn_keepScreen2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PowerManagerActivity.this, KeepScreenOn2Activity.class));
            }
        });

        final Button button3 = (Button) findViewById(R.id.btn_wakelock);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PowerManagerActivity.this, WakeLockActivity.class));
            }
        });

        final Button button4 = (Button) findViewById(R.id.btn_wakelock2);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, SemThreads.class));
            }
        });
    }
}
