package br.ufpe.cin.if710.activitiesintents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TxtActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt);

        TextView assunto = (TextView) findViewById(R.id.assunto);
        TextView texto = (TextView) findViewById(R.id.texto);

        Intent i = getIntent();
        //i.getExtras()
        String str_assunto = i.getStringExtra(Intent.EXTRA_SUBJECT);
        String str_txt = i.getStringExtra(Intent.EXTRA_TEXT);

        assunto.setText(str_assunto);
        texto.setText(str_txt);
    }
}
