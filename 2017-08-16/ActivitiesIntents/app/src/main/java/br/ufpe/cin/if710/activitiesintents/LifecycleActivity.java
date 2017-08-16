package br.ufpe.cin.if710.activitiesintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LifecycleActivity extends Activity {
    Button botao ;
    TextView textoDigitado;

    String KEY = "oQueFoiDigitado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        Toast.makeText(this," onCreate() ",Toast.LENGTH_SHORT).show();
        botao = (Button) findViewById(R.id.botao);
        textoDigitado = (TextView) findViewById(R.id.textoDigitado);
        final EditText campoTexto = (EditText) findViewById(R.id.campoTexto);

        if (savedInstanceState!=null) {
            String digitouAlgo = savedInstanceState.getString(KEY);
            if (digitouAlgo != null) {
                textoDigitado.setText(digitouAlgo);
            }
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoTexto.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext()," digite algo, por favor... ",Toast.LENGTH_SHORT).show();
                }
                else {
                    textoDigitado.setText(campoTexto.getText().toString());
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this," onStart() ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this," onResume() ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this," onPause() ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this," onStop() ",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this," onDestroy() ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY, textoDigitado.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
