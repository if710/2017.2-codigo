package br.ufpe.cin.if710.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicPlayerNoBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_no_binding);

        final Intent serviceIntent = new Intent(this,MusicPlayerNoBindingService.class);

        final Button startButton = (Button) findViewById(R.id.btn_StartService);
        final Button stopButton = (Button) findViewById(R.id.btn_StopService);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                startService(serviceIntent );
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                stopService(serviceIntent );
            }
        });
    }
}
