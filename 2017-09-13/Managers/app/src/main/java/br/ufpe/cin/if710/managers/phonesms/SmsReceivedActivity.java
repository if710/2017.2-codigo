package br.ufpe.cin.if710.managers.phonesms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.if710.managers.R;

public class SmsReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_received);

        Intent i = getIntent();
        String msgFrom = i.getStringExtra("msgFrom");
        String msgBody = i.getStringExtra("msgBody");
        if ((msgFrom != null) && (msgBody != null)) {
            TextView from = (TextView) findViewById(R.id.msgFrom);
            from.setText(msgFrom);
            TextView body = (TextView) findViewById(R.id.msgBody);
            body.setText(msgBody);
        }
        else {
            Toast.makeText(this, "Intent vazio", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
