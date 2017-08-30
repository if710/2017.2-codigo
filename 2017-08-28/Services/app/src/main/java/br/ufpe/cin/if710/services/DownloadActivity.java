package br.ufpe.cin.if710.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DownloadActivity extends Activity {

    Button botaoDownload;

    public static final String downloadLink = "https://cacm.acm.org/system/assets/0002/6368/Moshe_Vardi.large.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        botaoDownload = (Button) findViewById(R.id.botaoDownload);
        botaoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoDownload.setEnabled(false);
                Intent downloadService = new Intent(getApplicationContext(),DownloadService.class);
                downloadService.setData(Uri.parse(downloadLink));
                startService(downloadService);
            }
        });

        Button botaoVisualizar = (Button) findViewById(R.id.botaoVisualizar);

        botaoVisualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewFile = new Intent(getApplicationContext(),DownloadViewActivity.class);
                startActivity(viewFile);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f = new IntentFilter(DownloadService.DOWNLOAD_COMPLETE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onDownloadCompleteEvent, f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(onDownloadCompleteEvent);
    }

    private BroadcastReceiver onDownloadCompleteEvent=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent i) {
            botaoDownload.setEnabled(true);
            Toast.makeText(ctxt, "Download finalizado!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ctxt,DownloadViewActivity.class));
        }
    };
}
