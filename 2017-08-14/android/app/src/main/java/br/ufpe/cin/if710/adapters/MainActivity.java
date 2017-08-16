package br.ufpe.cin.if710.adapters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EstadosActivity.class);
                startActivity(i);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(i);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PessoasActivity.class);
                startActivity(i);
            }
        });
        Button b4 = (Button) findViewById(R.id.btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CustomAdapterActivity.class);
                startActivity(i);
            }
        });

        Button b5 = (Button) findViewById(R.id.btn5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = "o texto que porventura tenha sido digitado...";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                //se nao marcar isto aqui, pode dar pau
                intent.setType("text/plain");
                //nao influenciam o processo de resolucao
                intent.putExtra(Intent.EXTRA_SUBJECT, "algum assunto");
                intent.putExtra(Intent.EXTRA_TEXT, txt);

                startActivity(intent);
            }
        });
    }
}
