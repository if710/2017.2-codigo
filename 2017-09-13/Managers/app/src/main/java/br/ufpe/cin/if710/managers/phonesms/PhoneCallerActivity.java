package br.ufpe.cin.if710.managers.phonesms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufpe.cin.if710.managers.R;

public class PhoneCallerActivity extends AppCompatActivity implements View.OnClickListener {
    EditText num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dial_call);

        num = (EditText) findViewById(R.id.numberToDial);

        Button dialer = (Button) findViewById(R.id.btnDial);
        dialer.setOnClickListener(this);
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onClick(View v) {
        String numberToDial = num.getText().toString();
        Uri data = Uri.parse("tel:"+numberToDial);
        //precisa de permissao CALL_PHONE
        Intent i = new Intent(Intent.ACTION_CALL, data);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        }

    }
}
