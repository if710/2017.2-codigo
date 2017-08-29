package br.ufpe.cin.if710.services;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class DownloadViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_view);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File imageFile = new File(root, Uri.parse(DownloadActivity.downloadLink).getLastPathSegment());
            if (imageFile.exists()) {
                ImageView imageview = (ImageView) findViewById(R.id.imagemDownload);
                imageview.setImageURI(Uri.parse("file://" + imageFile.getAbsolutePath()));
            }
            else {
                Toast.makeText(this, "Arquivo nao existe", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Armazenamento externo nao esta montado...", Toast.LENGTH_SHORT).show();
        }
    }
}
