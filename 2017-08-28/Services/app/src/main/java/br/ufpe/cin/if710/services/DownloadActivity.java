package br.ufpe.cin.if710.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DownloadActivity extends Activity {

    Button botaoDownload;

    public static final String downloadLink = "https://ochamadodaalma.files.wordpress.com/2016/01/furacao.jpg";

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

}
