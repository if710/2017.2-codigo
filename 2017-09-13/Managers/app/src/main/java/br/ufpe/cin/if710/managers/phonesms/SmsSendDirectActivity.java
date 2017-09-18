package br.ufpe.cin.if710.managers.phonesms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.ufpe.cin.if710.managers.R;

public class SmsSendDirectActivity extends AppCompatActivity implements View.OnClickListener{
    EditText msg;
    TextView contato;
    Button enviar,pickContact;

    static final int PEGAR_CONTATO_REQ = 1;

    static final String SENT_BROADCAST = "SMS_ENVIADO";
    static final String DELIVERED_BROADCAST = "SMS_ENTREGUE";

    boolean contatoEscolhido = false;
    String telContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean sendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;

        if (sendSMS) {
            setContentView(R.layout.activity_sms_sender);

            contato = (TextView) findViewById(R.id.contato);

            msg = (EditText) findViewById(R.id.msgToSend);

            pickContact = (Button) findViewById(R.id.pickContact);
            pickContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);//apenas contatos com telefone
                    startActivityForResult(i, PEGAR_CONTATO_REQ);
                }
            });


            enviar = (Button) findViewById(R.id.btnSend);

            enviar.setOnClickListener(this);
        }
        else {
            Toast.makeText(this,"Conceda permissões em settings", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        enviar.setEnabled(false);
        telContato = "";
        contato.setText("Nenhum contato escolhido!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PEGAR_CONTATO_REQ) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();

                //pegar apenas o numero de telefone
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                //fazendo query direto na thread principal...
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // pega o numero de telefone
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                telContato = cursor.getString(column);
                //altera textview
                contato.setText(telContato);
                //habilita botao
                enviar.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        String message = msg.getText().toString();
        registerReceiver(enviadoReceiver, new IntentFilter(SENT_BROADCAST));
        registerReceiver(entregueReceiver, new IntentFilter(DELIVERED_BROADCAST));

        PendingIntent piEnvio   = PendingIntent.getBroadcast(this,0,new Intent(SENT_BROADCAST),0);
        PendingIntent piEntrega = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED_BROADCAST),0);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                telContato,
                null,
                message,
                piEnvio,
                piEntrega);
    }

    BroadcastReceiver enviadoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(getBaseContext(), "SMS enviado", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(getBaseContext(), "Falha geral", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(getBaseContext(), "Sem serviço", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                    break;
            }

            unregisterReceiver(this);
        }
    };

    BroadcastReceiver entregueReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(getBaseContext(), "SMS entregue", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getBaseContext(), "SMS não foi entregue", Toast.LENGTH_SHORT).show();
                    break;
            }
            unregisterReceiver(this);
        }
    };


}