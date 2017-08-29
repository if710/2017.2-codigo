package br.ufpe.cin.if710.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayerWithBindingService extends Service {
    private final String TAG = "MusicPlayerWithBindingService";
    private static final int NOTIFICATION_ID = 2;
    private MediaPlayer mPlayer;
    private int mStartID;

    @Override
    public void onCreate() {
        super.onCreate();

        // configurar media player
        mPlayer = MediaPlayer.create(this, R.raw.moonlightsonata);

        if (null != mPlayer) {
            //fica em loop
            mPlayer.setLooping(true);
        }


        // cria notificacao na area de notificacoes para usuario voltar p/ Activity
        final Intent notificationIntent = new Intent(getApplicationContext(), MusicPlayerWithBindingActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        final Notification notification = new Notification.Builder(
                getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true).setContentTitle("Music Service rodando")
                .setContentText("Clique para acessar o player!")
                .setContentIntent(pendingIntent).build();

        // inicia em estado foreground, para ter prioridade na memoria
        // evita que seja facilmente eliminado pelo sistema
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (null != mPlayer) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    public void playMusic() {
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }


    private final IBinder mBinder = new MusicBinder();
    public class MusicBinder extends Binder {
        MusicPlayerWithBindingService getService() {
            // retorna a instancia do Service, para que clientes chamem metodos publicos
            return MusicPlayerWithBindingService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
