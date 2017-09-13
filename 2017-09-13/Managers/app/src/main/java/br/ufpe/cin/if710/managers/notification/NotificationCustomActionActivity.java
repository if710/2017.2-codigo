package br.ufpe.cin.if710.managers.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.ufpe.cin.if710.managers.R;

public class NotificationCustomActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_custom_action);
        String nome = getIntent().getStringExtra("informacao");
        TextView infoExtra = (TextView) findViewById(R.id.infoExtra);
        infoExtra.setText(nome);
    }
}
