package br.ufpe.cin.if710.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MusicPlayerWithBindingActivity extends Activity {
    MusicPlayerWithBindingService musicPlayerService;
    boolean isBound = false;
    final String TAG = "MusicBindingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_with_binding);
        // intent usado para iniciar o service
        final Intent musicServiceIntent = new Intent(this, MusicPlayerWithBindingService.class);
        startService(musicServiceIntent);


        final Button startButton = (Button) findViewById(R.id.botaoPlay);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                if (isBound) {
                    musicPlayerService.playMusic();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Service ainda não fez binding", Toast.LENGTH_SHORT).show();
                }

            }
        });
        final Button pauseButton = (Button) findViewById(R.id.botaoPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View src) {
                if (isBound) {
                    musicPlayerService.pauseMusic();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Service ainda não fez binding", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ServiceConnection sConn = new ServiceConnection(){
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "Desconectou bound service");
            musicPlayerService = null;
            isBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "Binding com service");
            musicPlayerService = ((MusicPlayerWithBindingService.MusicBinder) service).getService();
            isBound = true;
        }
    };

        @Override
    protected void onStart() {
        super.onStart();
        if (!isBound) {
            Toast.makeText(getApplicationContext(), "Binding...", Toast.LENGTH_SHORT).show();
            Intent bindIntent = new Intent(this,MusicPlayerWithBindingService.class);
            isBound = bindService(bindIntent, sConn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Unbinding...", Toast.LENGTH_SHORT).show();
        unbindService(sConn);
        isBound = false;
    }



    /* nao eh interessante, pois para a musica ao eliminar Activity da Memoria
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Intent bindIntent = new Intent(this,MusicPlayerWithBindingService.class);
            stopService(bindIntent);
        }
    }
/**/

}
