package br.ufpe.cin.if710.managers.phonesms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.ufpe.cin.if710.managers.R;

public class SmsSenderActivity extends AppCompatActivity {
    EditText msg;
    TextView contato;
    Button enviar,pickContact;

    static final int PEGAR_CONTATO_REQ = 42;

    boolean contatoEscolhido = false;
    String telContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                Uri data = Uri.parse("smsto:"+telContato);
                Intent i = new Intent(Intent.ACTION_SENDTO, data);
                i.putExtra("sms_body",message);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        enviar.setEnabled(false);
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
                //Uri que aponta direto para um contato
                //content://contacts/1 -- que vai direto para um contato especifico
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
}
